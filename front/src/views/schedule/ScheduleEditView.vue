
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>스케쥴 수정</h2>
    <el-select v-model="schedule.course" class="mb-2 w-50" disabled>
      <el-option
          v-for="key in Object.keys(courseSet)"
          :key="key"
          :label="courseSet[key]"
          :value="key"
      />
    </el-select>
    <el-input v-model="schedule.studentName" class="mb-2 w-50" type="text" disabled/>
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
    <el-container>
      <el-button type="primary" @click="edit()">수정</el-button>
      <el-button type="danger" @click="deleteSchedule()">삭제</el-button>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import {useScheduleStore} from "@/stores/schedule";
import axios from "axios";
import router from "@/router";

const scheduleStore = useScheduleStore();

const schedule = ref({...scheduleStore.getSchedule});
const teachers = ref([]);
const selectTime = ref(new Date());

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

const edit = () => {
  const check = confirm("해당 스케쥴을 수정하시겠습니까?");
  if (!check) return;

  axios.patch(`/api/schedules/${schedule.value.id}`, {
    time: stringOfDate(selectTime.value),
    teacherId: schedule.value.teacherId,
  }).then(() => {
    router.replace({name: "schedules"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

const deleteSchedule = () => {
  const check = confirm("해당 스케쥴을 삭제하시겠습니까?");
  if (!check) return;

  axios.delete(`/api/schedules/${schedule.value.id}`)
      .then(() => {
        scheduleStore.delete();
        router.replace({name: "schedules"})
      })
}

onMounted(() => {
  axios.get(`/api/teachers?course=${schedule.value.course}`)
      .then(res => {
            res.data.forEach(teacher => {
              teachers.value.push(teacher);
            })
          }
      ).catch(err => {
        const result = err.response.data;
        alert(result.message);
      });

  const year = schedule.value.time.substring(0, 4);
  const month = schedule.value.time.substring(5, 7);
  const day = schedule.value.time.substring(8, 10);
  const hour = schedule.value.time.substring(11, 13);
  const minute = schedule.value.time.substring(14, 16);

  selectTime.value = new Date(Number(year), Number(month) - 1, Number(day),
      Number(hour), Number(minute), 0);
})

</script>

<style>
</style>