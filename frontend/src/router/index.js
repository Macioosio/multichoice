import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Courses from '@/components/Courses'
import Tests from '@/components/Tests'
import Profile from '@/components/Profile'
import Areas from '@/components/Areas'

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
    }
  ]
})
