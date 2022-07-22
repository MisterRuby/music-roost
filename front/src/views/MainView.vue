<template>
  <el-container class="layout-container h-100">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="[1, 2]">
          <el-sub-menu index="1">
            <template #title>
              Student
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <RouterLink class="mx-1" to="/students">조회</RouterLink>
              </el-menu-item>
              <el-menu-item index="1-2">
                <RouterLink class="mx-1" to="/students/register">등록</RouterLink>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              Teacher
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1">
                <RouterLink class="mx-1" to="/teachers">조회</RouterLink>
              </el-menu-item>
              <el-menu-item index="2-2">
                <RouterLink class="mx-1" to="/teachers/register">등록</RouterLink>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              Schedule
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1">스케쥴 조회</el-menu-item>
              <el-menu-item index="3-2">스케쥴 등록</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-button class="fs-5" link @click="logout()">Logout</el-button>
        </div>
      </el-header>

      <el-main>
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
  import axios from "axios";
  import router from "@/router";

  const logout = () => {
    axios.get("/api/logout")
        .then(() => {
          router.replace({name: "home"})
        })
  }

</script>

<style scoped>
.layout-container .el-header {
  position: relative;
  background-color: var(--el-color-danger-light-7);
  color: var(--el-text-color-primary);
}
.layout-container .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-danger-light-8);
  border-right: 1px solid crimson;
}
.layout-container .el-menu {
  border-right: none;
}
.layout-container .el-main {
  padding: 0;
}
.layout-container .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>