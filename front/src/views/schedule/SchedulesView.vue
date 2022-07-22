<template>
  <el-container class="d-flex flex-column align-items-center w-100 h-100 pt-5">
    <h2>스케쥴 목록</h2>
    <el-table :data="schedules" @row-click="moveEdit" class="w-75">
      <el-table-column prop="time" label="레슨 시간" />
      <el-table-column prop="studentName" label="수강생" />
      <el-table-column prop="teacherName" label="담당 선생님" />
    </el-table>
    <el-footer>
      <el-select v-model="search.course" class="m-2" placeholder="전체">
        <el-option value="">전체</el-option>
        <el-option
            v-for="key in Object.keys(nameSet)"
            :key="key"
            :label="nameSet[key]"
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

  const nameSet = {
    STUDENT_NAME: "수강생",
    TEACHER_NAME: "선생님",
  }

  const schedules = ref([]);
  const search = ref({
    option: "",
    name: "",
    page: 1
  })

  const getList = () => {

    axios.get(`/api/schedules`, {params: search.value})
        .then(res => {
          schedules.value = [];
          res.data.forEach(schedule => {
            schedules.value.push(schedule);
          })
        })
        .catch(err => {
          const result = err.response.data;
          alert(result.message);
        });
  }

  onMounted(() => {
    getList();
  })

  const moveEdit = schedule => {
    router.push({name: "scheduleEdit", params: {scheduleId: schedule.id}})
  }
</script>

<style scoped>

</style>







<!--<template>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select" size="large">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--  <el-select v-model="value" class="m-2" placeholder="Select" size="small">-->
<!--    <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value"-->
<!--    />-->
<!--  </el-select>-->
<!--</template>-->

<!--<script lang="ts" setup>-->
<!--import { ref } from 'vue'-->

<!--const value = ref('')-->

<!--const options = [-->
<!--  {-->
<!--    value: 'Option1',-->
<!--    label: 'Option1',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option2',-->
<!--    label: 'Option2',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option3',-->
<!--    label: 'Option3',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option4',-->
<!--    label: 'Option4',-->
<!--  },-->
<!--  {-->
<!--    value: 'Option5',-->
<!--    label: 'Option5',-->
<!--  },-->
<!--]-->
<!--</script>-->
