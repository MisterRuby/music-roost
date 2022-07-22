<template>
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
            <el-menu-item index="3-1">
              <RouterLink class="mx-1" to="/schedules">조회</RouterLink>
            </el-menu-item>
<!--            <el-menu-item index="3-2">-->
<!--              <RouterLink class="mx-1" to="/schedules/register">등록</RouterLink>-->
<!--            </el-menu-item>-->
          </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<script setup lang="ts">
</script>

<style scoped>
  .el-aside {
    height: 100vh;
    color: var(--el-text-color-primary);
    background: var(--el-color-danger-light-8);
    border-right: 1px solid crimson;
  }
</style>