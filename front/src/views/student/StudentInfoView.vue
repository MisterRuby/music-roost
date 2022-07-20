<template>

  <div class="container w-100 h-100 d-flex flex-column align-items-center">
    <h2 >{{student.name}} 회원님</h2>
    <div class="w-50">
      <div class="d-flex justify-content-center">
        <span class="w-50 text-center">Phone Number</span>
        <span class="w-50 text-center">{{student.phoneNumber}}</span>
      </div>
      <div class="d-flex justify-content-center">
        <span class="w-50 text-center">Email</span>
        <span class="w-50 text-center">{{student.email}}</span>
      </div>
      <div class="d-flex justify-content-center">
        <span class="w-50 text-center">Course</span>
        <span class="w-50 text-center">{{student.course}}</span>
      </div>
      <div class="d-flex justify-content-center">
        <span class="w-50 text-center">Grade</span>
        <span class="w-50 text-center">{{student.grade}}</span>
      </div>
      <div class="d-flex justify-content-center">
        <span class="w-50 text-center">Since</span>
        <span class="w-50 text-center">{{student.since}}</span>
      </div>
    </div>
    <el-button type="primary" @click="moveEdit()">회원 정보 수정</el-button>
  </div>

</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  studentId: {
    type: Number,
    required: true,
  }
})

const courseSet = {
  PIANO: "피아노",
  VIOLIN: "바이올린",
  VIOLA: "비올라",
  FLUTE: "플루트",
  CLARINET: "클라리넷",
  VOCAL: "보컬",
}

const gradeSet = {
  BEGINNER: "초급",
  INTERMEDIATE: "중급",
  ADVANCED: "고급",
}

const student = ref({
  course: "",
  email: "",
  grade: "",
  id: 0,
  name: "",
  phoneNumber: "",
  since: ""
});

const moveEdit = () => {
  router.push({name: "studentEdit", params: {studentId: props.studentId}});
}

onMounted(() => {
  axios.get(`/api/students/${props.studentId}`)
      .then(res => {
            res.data.course = courseSet[res.data.course];
            res.data.grade = gradeSet[res.data.grade];
            student.value = res.data;
          }
      )
})

</script>

<style scoped>

</style>