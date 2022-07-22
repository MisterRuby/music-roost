
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>선생님 등록</h2>
    <el-input v-model="name" class="mb-2 w-50" type="text" placeholder="이름을 입력해주세요."
              pattern="^[가-힣a-zA-Z\d]{2,20}$" required/>
    <el-input v-model="email" class="mb-2 w-50" type="email" placeholder="이메일을 입력해주세요."
              pattern="^[a-zA-Z\d_!#$%&'\*+/=?{|}~^.-]+@[a-zA-Z\d.-]+$" required/>
    <el-input v-model="phoneNumber" class="mb-2 w-50" type="tel" placeholder="핸드폰 번호를 입력해주세요."
              pattern="^(010|011|016|017|019)-\d{3,4}-\d{4}$" required/>
    <el-select v-model="course" class="mb-2 w-50" placeholder="담당 과목을 선택해주세요.">
      <el-option
          v-for="course in courseGroup"
          :key="course.value"
          :label="course.label"
          :value="course.value"
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

const courseGroup = [
  {
    value: 'PIANO',
    label: '피아노',
  },
  {
    value: 'VIOLIN',
    label: '바이올린',
  },
  {
    value: 'VIOLA',
    label: '비올라',
  },
  {
    value: 'FLUTE',
    label: '플루트',
  },
  {
    value: 'CLARINET',
    label: '클라리넷',
  },
  {
    value: 'VOCAL',
    label: '보컬',
  },
];

const register = () => {
  axios.post("/api/teachers", {
    name: name.value,
    email : email.value,
    phoneNumber : phoneNumber.value,
    course : course.value,
  }).then(() => {
    router.replace({name: "teachers"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

</script>

<style>
</style>