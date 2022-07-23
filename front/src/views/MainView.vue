<template>
    <div class="common-layout">
      <el-container class="d-flex flex-column">
        <Header/>
        <el-container>
          <Aside />
          <el-main>
            <RouterView />
          </el-main>
        </el-container>
      </el-container>
    </div>
</template>

<script lang="ts" setup>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";</script>

<style scoped>
</style>