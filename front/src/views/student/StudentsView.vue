<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>수강생 목록</h2>
    <el-table :data="students" @row-click="moveEdit" class="w-75">
      <el-table-column prop="name" label="이름" />
      <el-table-column prop="email" label="이메일" />
      <el-table-column prop="phoneNumber" label="핸드폰" />
      <el-table-column prop="course" label="수강과목" />
      <el-table-column prop="grade" label="등급" />
      <el-table-column prop="since" label="가입일자" />
    </el-table>
    <el-footer>
      <el-select v-model="search.course" class="m-2" placeholder="수강과목">
        <el-option value="">전체</el-option>
        <el-option
            v-for="key in Object.keys(courseSet)"
            :key="key"
            :label="courseSet[key]"
            :value="key"
        />
      </el-select>
      <el-select v-model="search.grade" class="m-2" placeholder="등급">
        <el-option value="">전체</el-option>
        <el-option
            v-for="key in Object.keys(gradeSet)"
            :key="key"
            :label="gradeSet[key]"
            :value="key"
        />
      </el-select>
      <el-button class="text-center" type="primary" @click="getList()">검색</el-button>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
  import axios from "axios";
  import {onMounted, ref} from "vue";
  import router from "@/router";

  const courseSet = {
    PIANO: "피아노",
    VIOLIN: "바이올린",
    VIOLA: "비올라",
    FLUTE: "플루트",
    CLARINET: "클라리넷",
    VOCAL: "보컬",
  }

  const gradeSet = {
    BEGINNER: "초급",
    INTERMEDIATE: "중급",
    ADVANCED: "고급",
  }

  const students = ref([]);
  const search = ref({
    course: "",
    grade: "",
    name: "",
    page: 1
  })

  const getList = () => {
    students.value = [];
    axios.get(`/api/students`, {params: search.value})
        .then(res => {
              res.data.forEach((r: any) => {
                r.course = courseSet[r.course];
                r.grade = gradeSet[r.grade];
                students.value.push(r);
              })
            }
        ).catch(err => {
          const result = err.response.data;
          alert(result.message);
        });
  }

  onMounted(() => {
    getList();
  })

  const moveEdit = student => {
    router.push({name: "studentEdit", params: {studentId: student.id}})
  }
</script>

<style scoped>

</style>







<!--<template>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select" size="large">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select" size="small">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--</template>-->

<!--<script lang="ts" setup>-->
<!--import { ref } from 'vue'-->

<!--const value = ref('')-->

<!--const options = [-->
<!--  {-->
<!--    value: 'Option1',-->
<!--    label: 'Option1',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option2',-->
<!--    label: 'Option2',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option3',-->
<!--    label: 'Option3',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option4',-->
<!--    label: 'Option4',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option5',-->
<!--    label: 'Option5',-->
<!--  },-->
<!--]-->
<!--</script>-->
