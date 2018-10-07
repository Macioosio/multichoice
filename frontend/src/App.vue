<template>
  <div id="app" class="md-alignment-[center]-[center]">
    <AppHeader></AppHeader>
    <Navigation></Navigation>
    <router-view/>
  </div>
</template>

<script>
import Navigation from './components/Navigation'
import AppHeader from './components/AppHeader'

export default {
  name: 'App',
  components: {
    'AppHeader': AppHeader,
    'Navigation': Navigation
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
