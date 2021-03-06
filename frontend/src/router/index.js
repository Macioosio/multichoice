import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Courses from '@/components/Courses'
import Tests from '@/components/Tests'
import TestAdding from '@/components/TestAdding'
import TestSolving from '@/components/TestSolving'
import TestResults from '@/components/TestResults'
import StudentsTests from '@/components/StudentsTests'
import Profile from '@/components/Profile'
import Areas from '@/components/Areas'
import Questions from '@/components/Questions'
import QuestionAdding from '@/components/QuestionAdding'
import Login from '@/components/Login'
import Register from '@/components/Register'

Vue.use(Router)

export const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: true }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/courses',
      name: 'Courses',
      component: Courses,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/tests',
      name: 'Tests',
      component: Tests,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/tests/add',
      name: 'AddTest',
      component: TestAdding,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/tests/edit/:testId',
      name: 'EditTest',
      component: TestAdding,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/test/solving/:testId',
      name: 'TestSolving',
      component: TestSolving,
      props: true,
      meta: { requiresAuth: true, teacherAuth: false, studentAuth: true }
    },
    {
      path: '/test/results/:testId',
      name: 'TestResults',
      component: TestResults,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/tests/mine',
      name: 'StudentsTests',
      component: StudentsTests,
      meta: { requiresAuth: true, teacherAuth: false, studentAuth: true }
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: true }
    },
    {
      path: '/student/profile',
      name: 'Profile',
      component: Profile,
      meta: { requiresAuth: true, teacherAuth: false, studentAuth: true }
    },
    {
      path: '/course/:courseId/areas',
      name: 'Areas',
      component: Areas,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/courses/delete/:courseId',
      name: 'CoursesWithDelete',
      component: Courses,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/questions',
      name: 'AllQuestions',
      component: Questions,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/area/:areaId/questions/',
      name: 'QuestionsFromArea',
      component: Questions,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/course/:courseId/questions',
      name: 'QuestionsFromCourse',
      component: Questions,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/questions/delete/:questionId',
      name: 'QuestionsWithDelete',
      component: Questions,
      props: true
    },
    {
      path: '/area/:areaId/questions/add',
      name: 'QuestionAddingFromArea',
      component: QuestionAdding,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/course/:courseId/questions/add',
      name: 'QuestionAddingFromCourse',
      component: QuestionAdding,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/questions/edit/:questionId',
      name: 'EditQuestion',
      component: QuestionAdding,
      props: true,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    },
    {
      path: '/questions/add',
      name: 'AddQuestion',
      component: QuestionAdding,
      meta: { requiresAuth: true, teacherAuth: true, studentAuth: false }
    }
  ]
})

router.beforeEach((to, from, next) => {
  let authToken = sessionStorage.getItem('user-token')
  let authority = sessionStorage.getItem('user-authority')
  if (to.meta.requiresAuth) {
    if (!authToken || !authority) {
      next({name: 'Login'})
    } else if (to.meta.teacherAuth) {
      if (authority === 'TEACHER') {
        next()
      } else {
        next('/home')
      }
    } else if (to.meta.studentAuth) {
      if (authority === 'STUDENT') {
        next()
      } else {
        next('/home')
      }
    }
  } else {
    next()
  }
}
)
