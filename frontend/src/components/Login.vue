<template>
  <div class="container padding-class">
    <form class="login" @submit.prevent="login">
      <h2 class = "subtitle">Logowanie</h2>
      <div class="small-padding-class">
      <label class="label">Email</label>
      <input class="input input-width" required v-model="email" type="text" placeholder="email@email.com"/>
      </div>
      <div class="small-padding-class">
      <label class="label">Hasło</label>
      <input class="input input-width" required v-model="password" type="password" placeholder="Password"/>
      </div>
      <button class="button" type="submit">Zaloguj się</button>
      <md-snackbar :md-position="snackBarPosition" :md-duration="snackBarDuration" :md-active.sync="showSnackbar" md-persistent>
        <span>Logowanie nie powiodło się</span>
      </md-snackbar>
    </form>
    <router-link to="/register">Zarejestruj się</router-link>
  </div>
</template>

<script>
/* eslint-disable handle-callback-err */
import axios from 'axios'
import {router} from '../router/index'

export default {
  name: 'Login',
  data () {
    return {
      email: '',
      password: '',
      showSnackbar: false,
      snackBarDuration: 10000,
      snackBarPosition: 'center'
    }
  },
  methods: {
    login () {
      axios
        .post('/api/user/login', {'email': this.email, 'password': this.password})
        .then(response => (this.handleLoginResponse(response)))
        .catch(error => (this.handleError()))
    },
    handleLoginResponse (response) {
      if (response.status === 200) {
        sessionStorage.setItem('user-token', response.headers.authorization)
        sessionStorage.setItem('user-authority', response.data.authorities[0].authority)
        sessionStorage.setItem('user-email', response.data.username)
        router.push('/')
      }
    },
    handleError () {
      sessionStorage.removeItem('user-token')
      sessionStorage.removeItem('user-authority')
      sessionStorage.removeItem('user-email')
      this.showSnackbar = true
    }
  }
}
</script>

<style scoped>
  .padding-class {
    padding: 25px;
  }
  .small-padding-class {
    padding: 10px;
  }
  .input-width {
    width: 30%;
  }
</style>
