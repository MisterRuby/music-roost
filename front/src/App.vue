<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <header>

    <div class="wrapper">

      <nav>
        <RouterLink class="mx-1" to="/">Home</RouterLink>
        <RouterLink class="mx-1" to="/login">login</RouterLink>
        <RouterLink class="mx-1" to="/signUp">Sign Up</RouterLink>
        <RouterLink class="mx-1" to="/students/register">StudentRegister</RouterLink>
        <RouterLink class="mx-1" to="/students">Student</RouterLink>
<!--        <RouterLink class="mx-1" to="/students/Info">Student Info</RouterLink>-->
      </nav>
    </div>
  </header>

  <!-- 위의 RouterLink 를 클릭하면 클릭한 화면이 해당 화면에 보이게 돰!-->
  <RouterView />
</template>

<style>

</style>
