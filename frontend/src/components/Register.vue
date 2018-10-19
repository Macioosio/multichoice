<template>
<div class="container padding-class">
  <form class="register" @submit.prevent="register">
    <h2 class = "subtitle">Rejestracja</h2>
    <div class="small-padding-class">
      <label class="label">Email</label>
      <input class="input input-width" required v-model="email" type="text" placeholder="email@email.com"/>
    </div>
    <div class="small-padding-class">
      <label class="label">Hasło</label>
      <input class="input input-width" required v-model="password" type="password" placeholder="Password"/>
    </div>
    <div class="small-padding-class">
      <label class="label">Powtórz hasło</label>
      <input class="input input-width" required v-model="passwordRepeat" type="password" placeholder="Password"/>
    </div>
    <div class="small-padding-class">
    <md-radio v-model="role" value="STUDENT" selected>Student</md-radio>
    <md-radio v-model="role" value="TEACHER">Nauczyciel</md-radio>
    </div>
    <button class="button" type="submit">Zarejestruj się</button>
    <md-snackbar :md-position="snackBarPosition" :md-duration="snackBarDuration" :md-active.sync="showSnackbar" md-persistent>
      <span>{{snackBarText}}</span>
    </md-snackbar>
  </form>
</div>
</template>

<script>
/* eslint-disable handle-callback-err */

import axios from 'axios'
import {router} from '../router/index'

export default {
  name: 'Register',
  data () {
    return {
      email: '',
      password: '',
      passwordRepeat: '',
      role: 'STUDENT',
      showSnackbar: false,
      snackBarText: '',
      snackBarDuration: 10000,
      snackBarPosition: 'center'
    }
  },
  methods: {
    register () {
      if (this.password !== this.passwordRepeat) {
        this.showSnackbar = true
        this.snackBarText = 'Podane hasła nie są jednakowe'
      } else {
        let userData = {'email': this.email, 'password': this.password}
        if (this.role === 'STUDENT') {
          this.postStudent(userData)
        }
        if (this.role === 'TEACHER') {
          this.postTeacher(userData)
        }
      }
    },
    postStudent (studentData) {
      axios
        .post('/api/students', studentData)
        .then(() => (router.push('/login')))
        .catch(err => this.handleError())
    },
    postTeacher (teacherData) {
      axios
        .post('/api/teachers', teacherData)
        .then(() => (router.push('/login')))
        .catch(err => this.handleError())
    },
    handleError () {
      this.showSnackbar = true
      this.snackBarText = 'Ten adres email jest już w użyciu'
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
