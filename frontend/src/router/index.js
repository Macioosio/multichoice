import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Questions from '@/components/Questions'
import Tests from '@/components/Tests'
import Profile from '@/components/Profile'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/questions',
      name: 'Questions',
      component: Questions
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
    }
  ]
})
