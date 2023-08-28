<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">
      <div style="margin-right: 25px;">
        <RouterLink to="/">
          <a class="btn-login btn btn-primary" @click="Click" role="button">logout</a>
        </RouterLink>
      </div>
    </form>
  </nav>
  <!--  navbar per la navigazione dell'amministratore loggato-->

  <ul class="title-main navbar-ripetizioni nav nav-tabs">
    <li class="nav-item">
      <RouterLink to="/Admin"><a class="nav-link " aria-current="page" href="#">Pagina Amministratore</a></RouterLink>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle active" data-toggle="dropdown"  role="button" aria-expanded="false">Docenti</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddProf"><a class="dropdown-item" href="#">Aggiungi Docente</a></RouterLink></li>
        <li><RouterLink to="/DelProf"><a class="dropdown-item" href="#">Elimina Docente</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
        <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
        <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
      </ul>
    </li>

  </ul>

  <div class="logged-container title-container container">
    <h1 class="title-main">Eliminazione Docente</h1>
  </div>

<!--  &lt;!&ndash;FORM&ndash;&gt;
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">Name</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name">

      </div>
      <div class="form-group">
        <label for="surname">Surname</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="surname">
      </div>

      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-group-orange form-control" id="email" v-model="email">
      </div>

      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>-->
  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Nome</th>
        <th scope="col">Cognome</th>
        <th scope="col">Email</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="teacher in teachers" :key="teacher.teacherId">
        <!--        <td>{{ availability.teacherId }}</td>-->
        <td>{{ teacher.name }}</td>
        <td>{{ teacher.surname }}</td>
        <td>{{ teacher.email }}</td>
        <td><a class="btn-login btn btn-primary" role="button" @click="deleteTeacher(teacher.teacherId)">Elimina</a></td>
      </tr>

      </tbody>
    </table>
  </div>


</template>

<script>
//import axios from "axios";
import $ from 'jquery';
import {teacherService} from "@/Service/teachersService";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "DeleteProf",
  data: function(){
    return{
      isSigned:false,
      /*name:'',
      surname:'',
      email:'',*/
      teachers:{}

    }
  },
  created: async function() {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      this.loading = false;
      this.teachers = response.data;
      console.log(this.teachers);
    } catch (e) {
      console.log(e);
    }
  },
  methods:{
    /*add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'deleteProf',
        name:this.name,
        surname:this.surname,
        email: this.email,
      };
      if(localStorage.getItem("isLogged")=="true"&&localStorage.getItem("admin")=="true") {
        axios.get(url, {params}) /!*prima effettuiamo la http request async*!/
            .then(response => {         /!*solo una volta eseguita la request passiamo a gestire la risposta*!/
              if (response.data === "Removed") {
                console.log(" aggiunto con successo")
                this.name = '';
                this.surname = '';
                this.email = '';
              } else {
                alert("problema aggiunta docente");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    },*/
   /* $.post(url,{action: 'submitLogin',email: this.email,password: this.password},
            function(data){
              console.log(data)
            }
            )*/
    deleteTeacher(id){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'deleteTeacher',
        teacherId:id
      };
      if(localStorage.getItem("isLogged")=="true"&&localStorage.getItem("admin")=="true") {
        $.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "Removed") {
                console.log(" eliminato con successo")
                const index = this.teachers.findIndex(teacher => teacher.teacherId === id);
                if (index !== -1) {
                  this.teachers.splice(index, 1); // Rimuovi la riga corrispondente dalla lista
                }

                this.teacherId = '';
              } else {
                alert("problema eliminazione docente");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    }
  }
}
</script>

<style scoped>

</style>