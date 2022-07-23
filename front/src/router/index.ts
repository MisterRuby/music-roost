import {createRouter, createWebHistory} from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import LoginView from "../views/LoginView.vue";
import SignUpView from "../views/SignUpView.vue";

import StudentRegisterView from "../views/student/StudentRegisterView.vue";
import StudentsView from "../views/student/StudentsView.vue";
import StudentEditView from "../views/student/StudentEditView.vue";

import TeacherRegisterView from "../views/teacher/TeacherRegisterView.vue";
import TeachersView from "../views/teacher/TeachersView.vue";
import TeacherEditView from "../views/teacher/TeacherEditView.vue";

import ScheduleRegisterView from "../views/schedule/ScheduleRegisterView.vue";
import SchedulesView from "../views/schedule/SchedulesView.vue";
import ScheduleEditView from "../views/schedule/scheduleEditView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },

    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/signUp",
      name: "signUp",
      component: SignUpView,
    },
    {
      path: "/main",
      name: "main",
      component: MainView,
      children: [
          // student
        {
          path: "/students/register",
          name: "studentRegister",
          component: StudentRegisterView,
        },
        {
          path: "/students",
          name: "students",
          component: StudentsView,
        },
        {
          path: "/students/edit",
          name: "studentEdit",
          component: StudentEditView,
          props: true
        },
          // teacher
        {
          path: "/teachers/register",
          name: "teacherRegister",
          component: TeacherRegisterView,
        },
        {
          path: "/teachers",
          name: "teachers",
          component: TeachersView,
        },
        {
          path: "/teachers/edit",
          name: "teacherEdit",
          component: TeacherEditView,
          props: true
        },
          // schedule
        {
          path: "/schedules/register",
          name: "scheduleRegister",
          component: ScheduleRegisterView,
          props: true
        },
        {
          path: "/schedules",
          name: "schedules",
          component: SchedulesView,
        },
        {
          path: "/schedules/edit",
          name: "scheduleEdit",
          component: ScheduleEditView,
          props: true
        },
      ]
    },
  ],
});

export default router;
