import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Courses from '@/components/Courses'
import Tests from '@/components/Tests'
import Profile from '@/components/Profile'
import Areas from '@/components/Areas'
import Questions from '@/components/Questions'
import QuestionAdding from '@/components/QuestionAdding'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/courses',
      name: 'Courses',
      component: Courses
    },
    {
      path: '/tests',
      name: 'Tests',
      component: Tests
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: '/course/:courseId/areas',
      name: 'Areas',
      component: Areas,
      props: true
    },
    {
      path: '/courses/delete/:courseId',
      name: 'CoursesWithDelete',
      component: Courses,
      props: true
    },
    {
      path: '/questions',
      name: 'AllQuestions',
      component: Questions
    },
    {
      path: '/area/:areaId/questions/',
      name: 'QuestionsFromArea',
      component: Questions,
      props: true
    },
    {
      path: '/course/:courseId/questions',
      name: 'QuestionsFromCourse',
      component: Questions,
      props: true
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
      props: true
    },
    {
      path: '/course/:courseId/questions/add',
      name: 'QuestionAddingFromCourse',
      component: QuestionAdding,
      props: true
    },
    {
      path: '/questions/edit/:questionId',
      name: 'EditQuestion',
      component: QuestionAdding,
      props: true
    },
    {
      path: '/questions/add',
      name: 'AddQuestion',
      component: QuestionAdding
    }
  ]
})
