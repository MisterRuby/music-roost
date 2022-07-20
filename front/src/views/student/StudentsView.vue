<template>
  <div class="d-flex justify-content-center w-100">
    <el-table :data="students" @row-click="moveInfo" class="w-50">
      <el-table-column prop="name" label="이름" />
      <el-table-column prop="email" label="이메일" />
      <el-table-column prop="phoneNumber" label="핸드폰" />
      <el-table-column prop="course" label="수강과목" />
      <el-table-column prop="grade" label="등급" />
    </el-table>
  </div>


</template>

<script setup lang="ts">
  import axios from "axios";
  import {ref} from "vue";
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
  axios.get("/api/students")
      .then(res => {
        res.data.forEach((r: any) => {
          r.course = courseSet[r.course];
          r.grade = gradeSet[r.grade];
          students.value.push(r);
        })
      }
  )

  const moveInfo = student => {
    router.push({name: "studentInfo", params: {studentId: student.id}})
    // axios.get(`api/students/${student.id}`)
    //     .then(() =>{
    //
    //     })
  }
</script>

<style scoped>

</style>
