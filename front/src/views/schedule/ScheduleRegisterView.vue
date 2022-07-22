
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>스케쥴 등록</h2>
    <el-select v-model="props.course" class="mb-2 w-50" >
      <el-option
          v-for="key in Object.keys(courseSet)"
          :key="key"
          :label="courseSet[key]"
          :value="key"
      />
    </el-select>
    <el-input v-model="props.studentName" class="mb-2 w-50" type="text" required/>
    <el-select v-model="schedule.teacherId" class="mb-2 w-50" >
      <el-option
          v-for="teacher in teachers"
          :key="teacher.id"
          :label="teacher.name"
          :value="teacher.id"
      />
    </el-select>
    <el-date-picker
        v-model="selectTime"
        type="datetime"
        placeholder="Select date and time"
        :default-time="selectTime"
        class="mb-2 w-50"
    />

    <el-button type="primary" class="w-50" @click="register()">등록</el-button>
  </el-container>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

interface Teacher {
  id : number | undefined,
  name: string | undefined,
  course: string | undefined
}

const props = defineProps({
  course : {
    type: String,
    required: true
  },
  studentName : {
    type: String,
    required: true
  },
  studentId : {
    type: Number,
    required: true
  },

});

const now = new Date();
const selectTime = ref(new Date(now.getFullYear(), now.getMonth(), now.getDate(), 10, 0, 0));
const schedule = ref({
  teacherId: undefined,
  studentId: undefined,
  time: ''
});

const teachers = ref([]);

const courseSet = {
  PIANO: "피아노",
  VIOLIN: "바이올린",
  VIOLA: "비올라",
  FLUTE: "플루트",
  CLARINET: "클라리넷",
  VOCAL: "보컬",
}

const stringOfDate = date => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();

  return `${year}-${month < 10 ? 0 : ''}${month}-${day < 10 ? 0 : ''}${day} `
      + `${hour < 10 ? 0 : ''}${hour}:${minute < 10 ? 0 : ''}${minute}`
}

const register = () => {
  schedule.value.time = stringOfDate(selectTime.value);
  axios.post("/api/schedules", schedule.value).then(() => {
    router.replace({name: "schedules"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

onMounted(() => {
  schedule.value.studentId = props.studentId;
  axios.get(`/api/teachers`,
      {params: {
      course: props.course
    }}).then(res => {
            res.data.forEach((teacher : Teacher)=> {
              teacher.course = courseSet[teacher.course];
              teachers.value.push(teacher);
            })
          }
      ).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
})

</script>

<style scoped>
</style>