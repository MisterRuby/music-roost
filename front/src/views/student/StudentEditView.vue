
<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>수강생 정보 수정</h2>
    <el-input v-model="student.name" class="mb-2 w-50" type="text" placeholder="이름을 입력해주세요."
              pattern="^[가-힣a-zA-Z\d]{2,20}$" required/>
    <el-input v-model="student.email" class="mb-2 w-50" type="email" placeholder="이메일을 입력해주세요."
              pattern="^[a-zA-Z\d_!#$%&'\*+/=?{|}~^.-]+@[a-zA-Z\d.-]+$" required/>
    <el-input v-model="student.phoneNumber" class="mb-2 w-50" type="tel" placeholder="핸드폰 번호를 입력해주세요."
              pattern="^(010|011|016|017|019)-\d{3,4}-\d{4}$" required/>
    <el-select v-model="student.course" class="mb-2 w-50" placeholder="수강 과목을 선택해주세요.">
      <el-option
          v-for="course in courseGroup"
          :key="course.value"
          :label="course.label"
          :value="course.value"
      />
    </el-select>
    <el-select v-model="student.grade" class="mb-2 w-50" placeholder="수강 등급을 선택해주세요.">
      <el-option v-for="grade in gradeGroup"
          :key="grade.value"
          :label="grade.label"
          :value="grade.value"
      />
    </el-select>
    <el-container>
      <el-button type="primary" class="mb-2" @click="edit()">회원 정보 수정</el-button>
      <el-button type="danger" @click="deleteStudent()">회원 정보 삭제</el-button>
    </el-container>
  </el-container>
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

const student = ref({
  id: 0,
  name: "",
  email: "",
  phoneNumber: "",
  course: "",
  grade: "",
});

const courseGroup = [
  {
    value: 'PIANO',
    label: '피아노',
  },
  {
    value: 'VIOLIN',
    label: '바이올린',
  },
  {
    value: 'VIOLA',
    label: '비올라',
  },
  {
    value: 'FLUTE',
    label: '플루트',
  },
  {
    value: 'CLARINET',
    label: '클라리넷',
  },
  {
    value: 'VOCAL',
    label: '보컬',
  },
];

const gradeGroup = [
  {
    value: 'BEGINNER',
    label: '초급',
  },
  {
    value: 'INTERMEDIATE',
    label: '중급',
  },
  {
    value: 'ADVANCED',
    label: '고급',
  },
];

onMounted(() => {
  axios.get(`/api/students/${props.studentId}`)
      .then(res => {
            student.value = res.data;
          }
      ).catch(err => {
        const result = err.response.data;
        alert(result.message);
      });
})

const edit = () => {
  const check = confirm("해당 수강생의 정보를 수정하시겠습니까?");
  if (!check) return;

  axios.patch(`/api/students/${props.studentId}`, {
    name: student.value.name,
    email : student.value.email,
    phoneNumber : student.value.phoneNumber,
    course : student.value.course,
    grade : student.value.grade,
  }).then(() => {
    router.replace({name: "students"})
  }).catch(err => {
    const result = err.response.data;
    alert(result.message);
  });
}

const deleteStudent = () => {
  const check = confirm("해당 수강생의 정보를 삭제하시겠습니까?");
  if (!check) return;

  axios.delete(`/api/students/${props.studentId}`)
      .then(() => {
        router.replace({name: "students"})
      })
}

</script>

<style>
</style>