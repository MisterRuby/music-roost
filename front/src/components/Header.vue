<template>
  <el-header>
    <div class="toolbar">
      <h2 class="fs-1 main pointer-event" @click="moveHome()">Music Roost</h2>
      <el-button class="fs-5" link @click="logout()">Logout</el-button>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import axios from "axios";
import router from "@/router";

const moveHome = () => {
  router.push({name: "home"});
}

const logout = () => {
  axios.get("/api/logout")
      .then(() => {
        router.replace({name: "home"})
      })
}
</script>

<style scoped>
.el-header {
  background-color: var(--el-color-danger-light-7);
  color: var(--el-text-color-primary);
}
.main {
  cursor: pointer;
}
.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}
</style>