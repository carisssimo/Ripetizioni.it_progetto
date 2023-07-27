<template>
  <div >
    <div class="form-container container align-items-center">
      <div class="title-main">1. Seleziona l'Utente!</div>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col">Cognome</th>
          <th scope="col">Email</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for=" user in users" :key="user.userID">
          <td>{{ user.userID}}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.surname }}</td>
          <td><a class="btn-login btn btn-primary" role="button" @click="select(user.userID)">Seleziona</a></td>
        </tr>

        </tbody>
      </table>

    </div>
  </div>
</template>

<script>
import {userService} from "@/Service/userService";

export default {
  name: "SelectUser",
  data(){
    return{
      users:{}
    }
  },
  created: async function(){
    try{
      let response=await userService.getAllUsers();
      this.users=response.data ;
      console.log(this.users);

    }catch(e){
      console.log(e);
    }

  }
}
</script>

<style scoped>

</style>