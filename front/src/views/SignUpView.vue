<template>
  <el-container class="d-flex justify-content-center align-items-center w-100 h-100">
    <el-form label-width="120px" class="signUp-box">
      <el-form-item label="ID">
        <el-input v-model="name" type="text" autocomplete="off" />
      </el-form-item>
      <el-form-item label="PASSWORD">
        <el-input v-model="password" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item >
        <el-button type="primary" @click="signUp()">회원가입</el-button>
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import router from "@/router";

const name = ref("");
const password = ref("");

const signUp = () => {
  axios.post("/api/signUp", {
    name: name.value,
    password: password.value,
  }).then(() => {
    router.push({name: "login"})
  })
}

</script>

<style scoped>
.signUp-box {
  width: 400px;
}
.signUp-box button {
  width: 100%;
}
</style>


