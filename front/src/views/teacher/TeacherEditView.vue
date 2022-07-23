
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>선생님 정보 수정</h2>
    <el-input v-model="teacher.name" class="mb-2 w-50" type="text" placeholder="이름을 입력해주세요."
              pattern="^[가-힣a-zA-Z\d]{2,20}$" required/>
    <el-input v-model="teacher.email" class="mb-2 w-50" type="email" placeholder="이메일을 입력해주세요."
              pattern="^[a-zA-Z\d_!#$%&'\*+/=?{|}~^.-]+@[a-zA-Z\d.-]+$" required/>
    <el-input v-model="teacher.phoneNumber" class="mb-2 w-50" type="tel" placeholder="핸드폰 번호를 입력해주세요."
              pattern="^(010|011|016|017|019)-\d{3,4}-\d{4}$" required/>
    <el-select v-model="teacher.course" class="mb-2 w-50" placeholder="수강 과목을 선택해주세요.">
      <el-option
          v-for="key in Object.keys(courseSet)"
          :key="key"
          :label="courseSet[key]"
          :value="key"
      />
    </el-select>
    <el-container>
      <el-button type="primary" class="mb-2" @click="edit()">수정</el-button>
      <el-button type="danger" @click="deleteTeacher()">삭제</el-button>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";
import {useTeacherStore} from "@/stores/teacher";

const teacherStore = useTeacherStore();
const teacher = ref({...teacherStore.getTeacher});

const courseSet = {
  PIANO: "피아노",
  VIOLIN: "바이올린",
  VIOLA: "비올라",
  FLUTE: "플루트",
  CLARINET: "클라리넷",
  VOCAL: "보컬",
}

const edit = () => {
  const check = confirm("해당 선생님의 정보를 수정하시겠습니까?");
  if (!check) return;

  axios.patch(`/api/teachers/${teacher.value.id}`, {
    name: teacher.value.name,
    email : teacher.value.email,
    phoneNumber : teacher.value.phoneNumber,
    course : teacher.value.course,
  }).then(() => {
    router.replace({name: "teachers"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

const deleteTeacher = () => {
  const check = confirm("해당 선생님의 정보를 삭제하시겠습니까?");
  if (!check) return;

  axios.delete(`/api/teachers/${teacher.value.id}`)
      .then(() => {
        teacherStore.delete();
        router.replace({name: "teachers"})
      })
}

</script>

<style>
</style>