<template>
  <el-container class="d-flex justify-content-center align-items-center w-100 h-100">
    <el-form label-width="120px" class="login-box">
      <el-form-item label="ID">
        <el-input v-model="name" type="text" autocomplete="off" />
      </el-form-item>
      <el-form-item label="PASSWORD">
        <el-input v-model="password" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item >
        <el-button type="primary" @click="login()">로그인</el-button>
      </el-form-item>
      <el-form-item>
        <div class="w-100 text-center" @click="moveSignUp()">계정 추가</div>
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const name = ref("");
  const password = ref("");

  const login = () => {
    axios.post("/api/login", {
      name: name.value,
      password : password.value
    }).then(() => {
      router.push({name: "students"})
    }).catch(err => {
      const result = err.response.data;
      alert(result.message);
    })
  }

  const moveSignUp = () => {
    router.push({name: "signUp"})
  }

</script>

<style scoped>
  .login-box {
    width: 400px;
  }
  .login-box button {
    width: 100%;
  }
</style>

