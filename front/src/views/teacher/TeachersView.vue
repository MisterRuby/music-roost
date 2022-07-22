<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>선생님 목록</h2>
    <el-table :data="teachers" @row-click="moveEdit" class="w-75">
      <el-table-column prop="name" label="이름" />
      <el-table-column prop="email" label="이메일" />
      <el-table-column prop="phoneNumber" label="핸드폰" />
      <el-table-column prop="course" label="수강과목" />
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
      <el-button class="text-center" type="primary" @click="getList()">검색</el-button>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
  import axios from "axios";
  import {onMounted, ref} from "vue";
  import router from "@/router";

  const courseSet = {
    PIANO: "피아노",
    VIOLIN: "바이올린",
    VIOLA: "비올라",
    FLUTE: "플루트",
    CLARINET: "클라리넷",
    VOCAL: "보컬",
  }

  const teachers = ref([]);
  const search = ref({
    course: "",
    name: "",
    page: 1
  })

  const getList = () => {
    teachers.value = [];
    axios.get(`/api/teachers`, {params: search.value})
        .then(res => {
              res.data.forEach(teacher => {
                teacher.course = courseSet[teacher.course];
                teachers.value.push(teacher);
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

  const moveEdit = teacher => {
    router.push({name: "teacherEdit", params: {teacherId: teacher.id}})
  }
</script>

<style scoped>

</style>