
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>수강생 등록</h2>
    <el-input v-model="name" class="mb-2 w-50" type="text" placeholder="이름을 입력해주세요."
              pattern="^[가-힣a-zA-Z\d]{2,20}$" required/>
    <el-input v-model="email" class="mb-2 w-50" type="email" placeholder="이메일을 입력해주세요."
              pattern="^[a-zA-Z\d_!#$%&'\*+/=?{|}~^.-]+@[a-zA-Z\d.-]+$" required/>
    <el-input v-model="phoneNumber" class="mb-2 w-50" type="tel" placeholder="핸드폰 번호를 입력해주세요."
              pattern="^(010|011|016|017|019)-\d{3,4}-\d{4}$" required/>
    <el-select v-model="course" class="mb-2 w-50" placeholder="수강 과목을 선택해주세요.">
      <el-option
          v-for="key in Object.keys(courseSet)"
          :key="key"
          :label="courseSet[key]"
          :value="key"
      />
    </el-select>
    <el-select v-model="grade" class="mb-2 w-50" placeholder="수강 등급을 선택해주세요.">
      <el-option
          v-for="key in Object.keys(gradeSet)"
          :key="key"
          :label="gradeSet[key]"
          :value="key"
      />
    </el-select>
    <el-button type="primary" class="w-50" @click="register()">등록</el-button>
  </el-container>
</template>

<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const name = ref("");
const email = ref("");
const phoneNumber = ref("");
const course = ref("");
const grade = ref("");

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

const register = () => {
  axios.post("/api/students", {
    name: name.value,
    email : email.value,
    phoneNumber : phoneNumber.value,
    course : course.value,
    grade : grade.value,
  }).then(() => {
    router.replace({name: "students"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

</script>

<style>
</style>