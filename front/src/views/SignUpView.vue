<template>
  <div class="d-flex flex-column w-50">
    <el-input v-model="name" class="mb-2" type="text" placeholder="아이디를 입력해주세요." />
    <el-input v-model="password" class="mb-2" type="password" placeholder="비밀번호를 입력해주세요." />
    <el-button type="primary" @click="login()">로그인</el-button>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";

const name = ref("");
const password = ref("");

const login = () => {
  axios.post("/api/signUp", {
    name: name.value,
    password: password.value,
  });
}

</script>

<style scoped>

</style>


