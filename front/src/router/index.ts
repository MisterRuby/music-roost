import {createRouter, createWebHistory} from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import LoginView from "../views/LoginView.vue";
import SignUpView from "../views/SignUpView.vue";

import StudentRegisterView from "../views/student/StudentRegisterView.vue";
import StudentsView from "../views/student/StudentsView.vue";
import StudentEditView from "../views/student/StudentEditView.vue";

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
          path: "/students/edit/:studentId",
          name: "studentEdit",
          component: StudentEditView,
          props: true
        },
      ]
    },
  ],
});

export default router;
