<template>
<div class="container title">
  <div class="md-layout md-gutter">
    <div class="md-layout-item"></div>
    <div class="md-layout-item"><h1>MULTICHOICE</h1></div>
    <div class="md-layout-item">
      <div v-if="email">
      <label class="label">{{email}}</label>
      <button class="button" v-on:click="logout">Wyloguj się</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import {router} from '../router/index'

export default {
  name: 'AppHeader',
  data () {
    return {
      email: ''
    }
  },
  methods: {
    logout () {
      sessionStorage.removeItem('user-token')
      sessionStorage.removeItem('user-authority')
      sessionStorage.removeItem('user-email')
      router.push('/login')
    }
  },
  watch: {
    $route (to, from) {
      this.email = sessionStorage.getItem('user-email')
    }
  }
}
</script>

<style scoped>

</style>
