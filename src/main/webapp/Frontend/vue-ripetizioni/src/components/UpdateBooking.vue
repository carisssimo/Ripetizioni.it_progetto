<template>
  <!-- NAVBAR PRINCIPALE -->
  <!-- <div class="container-fluid"> -->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">
      <div style="margin-right: 25px;">
        <router-link to="/">
          <a class="btn-login btn btn-primary" @click="Click" role="button">logout</a>
        </router-link>
      </div>
    </form>
  </nav>
  <!-- </div> -->

  <!--  navbar per la navigazione dell'utente loggato-->

  <ul class="title-main navbar-ripetizioni nav nav-tabs">
    <li class="nav-item">
      <RouterLink to="/PersonalPage"><a class="nav-link " aria-current="page" href="#">Pagina Personale</a></RouterLink>
    </li>
    <li class="nav-item">
      <RouterLink to="/update"><a class="nav-link active" href="#">Modifica </a></RouterLink>
    </li>
    <li class="nav-item">
      <RouterLink to="/smartBooking"><a class="nav-link" href="#">Prenota</a></RouterLink>
    </li>

  </ul>

  <!-- {{ this.teacherSelected }} {{ this.subjectSelected }} -->
  <div class="logged-container title-container container">
    <h1 class="title-main">Aggiorna la prenotazione o eliminala</h1>
  </div>

<!--vecchia tabella riutilizzabile se si vuole far vedere un ordinamento differente-->

<!--  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Slot Orario</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="availability in availabilities" :key="availability.availabilityID">
          <td>{{ printTeacherName(availability.teacherId) }}</td>
          <td>{{ printSubjectName(availability.subjectId) }}</td>
          <td>{{ availability.dayTime }}</td>
          <td>

            <a class="btn-login btn btn-primary" role="button" @click="delet(availability.availabilityID)">
              Cancella
            </a>
            <a class="btn-login btn btn-primary" role="button" @click="archiv(availability.availabilityID)">
              Effettuata
            </a>
          </td>

      </tr>
      </tbody>
    </table>
  </div>-->

  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Orario</th>
        <th v-for="day in days" :key="day.id" scope="col">{{ day.description }}</th>

      </tr>
      </thead>
      <tbody>
      <tr v-for="slot in slots" :key="slot.id">
        <td>{{ slot.description }}</td>
        <td v-for="day in days" :key="day.id" >
          <div  v-if="existAvailability(day.id, slot.id)==='true'" class="table_data_slot">
            <ul>
              <li>{{printTeacherName(getProfessor(day.id, slot.id))}}</li>
              <li>{{printSubjectName(getSubject(day.id, slot.id))}}</li>
              <li><a class="btn-login btn btn-primary" role="button" @click="delet(searchAvailability(day.id, slot.id))">
                Cancella
              </a></li>
              <li><a class="btn-login btn btn-primary" role="button" @click="archiv(searchAvailability(day.id, slot.id))">
                Effettuata
              </a></li>
            </ul>
          </div>
        </td>
      </tr>
      </tbody>

    </table>
  </div>
<!--
  <span style="padding: 0 10px;"></span>

  <div class="button-container container align-items-center">
    <router-link to="/Login">
      <a class="btn-l btn-login btn btn-success" role="button">Torna indietro</a>
    </router-link>
  </div>-->
</template>

<script>
import { teacherService } from "@/Service/teachersService";
import { subjectsService } from "@/Service/subjectsService";
import { availabilityService } from "@/Service/availabilityService";
import $ from 'jquery';
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";
import router from "@/router";

export default {
  name: "UpdateBooking",
  data() {
    return {
      loading: false,
      teachers: [],
      subjects: [],
      availabilities: [],
      days: {},
      slots: {},
      teacherSelected: "",
      subjectSelected: ""
    };
  },
  /*computed:{
    filteredAvailabilities: function(){
      let filteredAvailabilities[];
      let j=0;
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].booking === "attiva") {
          console.log(this.availabilities[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    }

  },*/
  created: async function () {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      if(localStorage.getItem("isLogged")==='true') {
        let response3 = await availabilityService.getAvailabilitiesByIDActive();
        if(response3==='invaliSession'){
          alert("sessioneInvalida")
          router.push("/")
        }else {
          this.availabilities = response3;
        }
      }
     else{
        alert("non sei loggato")
        router.push("/")
      }
      let response4 = await dayService.getAllDays();
      let response5 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response;
      this.subjects = response2;

      this.days = response4;
      this.slots = response5;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    getProfessor(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].teacherId;
        }
      }

    },
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      //cookieService.delete(localStorage.getItem('email'));
      const params = {
        action: 'logout',
      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem('isLogged');
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    getSubject(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].subjectId;
        }
      }

    },
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    existAvailability(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {


          console.log(day)
          console.log(slot)

          return 'true'

        }
      }
    },

    searchAvailability(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          return this.availabilities[i].availabilityID
        }
      }
    },

    archiv(id) {
      if(localStorage.getItem("isLogged")==='true') {
        console.log(id);
        const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";


        $.get(url, {action: "archiveAvailability", availabilityId: id})
            .then(response => {
              if (response === "booked") {
                console.log("archiviata con successo");
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              } else {
                alert("Archiviazione fallita");
              }
            })
            .catch(error => {
              console.error(error);
            });
      }
    },

    delet(id) {
      if(localStorage.getItem("isLogged")==='true') {
        console.log(id);
        const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";


        $.get(url, {action: "deleteAvailability", availabilityId: id})
            .then(response => {
              if (response === "booked") {
                console.log("Prenotato con successo");
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              } else {
                alert("Prenotazione fallita");
              }
            })
            .catch(error => {
              console.error(error);
            });
      }
    },
   /* Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'logout',
      };
      axios.get(url, {params})
          .then(response => {
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem("isLogged");
              this.$router.push("/Login");
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },*/
  }
};
</script>

<style scoped>

</style>
