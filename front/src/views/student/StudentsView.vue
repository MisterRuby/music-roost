<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>수강생 목록</h2>
    <el-table :data="students" @row-click="moveEdit" class="w-75">
      <el-table-column prop="name" label="이름" />
      <el-table-column prop="email" label="이메일" />
      <el-table-column prop="phoneNumber" label="핸드폰" />
      <el-table-column prop="course" label="수강과목" />
      <el-table-column prop="grade" label="등급" />
      <el-table-column prop="since" label="가입일자" />
    </el-table>
    <el-footer>
      <el-select v-model="search.course" class="m-2" placeholder="수강과목">
        <el-option value="">전체</el-option>
        <el-option
            v-for="key in Object.keys(courseSet)"
            :key="key"
            :label="courseSet[key]"
            :value="key"
        />
      </el-select>
      <el-select v-model="search.grade" class="m-2" placeholder="등급">
        <el-option value="">전체</el-option>
        <el-option
            v-for="key in Object.keys(gradeSet)"
            :key="key"
            :label="gradeSet[key]"
            :value="key"
        />
      </el-select>
      <el-button class="text-center" type="primary" @click="getList()">검색</el-button>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";
import router from "@/router";
import {useStudentStore} from "@/stores/student";

const studentStore = useStudentStore();

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

  const students = ref([]);
  const search = ref({
    course: "",
    grade: "",
    name: "",
    page: 1
  })

  const getList = () => {
    students.value = [];
    axios.get(`/api/students`, {params: search.value})
        .then(res => {
              res.data.forEach(student => {
                // student.course = courseSet[student.course];
                // student.grade = gradeSet[student.grade];
                students.value.push(student);
              })
            }
        ).catch(err => {
          const result = err.response.data;
          alert(result.message);
        });
  }

  onMounted(() => {
    getList();
  })

  const moveEdit = student => {
    studentStore.set(student);
    router.push({name: "studentEdit", params: {studentId: student.id}});
  }
</script>

<style scoped>

</style>