<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

      <div style="margin-right: 25px;">
        <RouterLink to="/"><a class="btn-login btn btn-primary" role="button" @click="Click">logout</a></RouterLink>
      </div>
    </form>
  </nav>
  <!--</div>-->

  <ul class="title-main navbar-ripetizioni nav nav-tabs">
    <li class="nav-item">
      <RouterLink to="/PersonalPage"><a aria-current="page" class="nav-link " href="#">Pagina Personale</a></RouterLink>
    </li>
    <li class="nav-item">
      <RouterLink to="/update"><a class="nav-link " href="#">Modifica </a></RouterLink>
    </li>
    <li class="nav-item">
      <RouterLink to="/smartBooking"><a class="nav-link active" href="#">Prenota</a></RouterLink>
    </li>

  </ul>

  <!--  {{this.teacherSelected}}
    {{this.subjectSelected}}-->
  <div class="logged-container title-container container">
    <h1 class="title-main">Scegli la ripe più adatta a te</h1>
  </div>


  <router-view></router-view>

  <!--  navbar per la navigazione dell'utente loggato-->


  <!--FORM per selezionare orario e materia
  <div class="form-container-selector container">
    <form class="form-inline">
      <label class="my-1 mr-2" for="inlineFormCustomSelectCourse">Materia</label>
      <select id="inlineFormCustomSelectCourse" class="custom-select my-1 mr-sm-2" v-model="subjectSelected">
        <option selected>Scegli la materia</option>
        <option v-for="subject in subjects" :key="subject.subjectID" >{{ subject.subjectName }}</option>
      </select>

      <label class="my-1 mr-2" for="inlineFormCustomSelectTime">Professore</label>
      <select id="inlineFormCustomSelectTime" class="custom-select my-1 mr-sm-2" v-model="teacherSelected">
        <option selected>Scegli professore</option>
        <option v-for="teacher in teachers" :key="teacher.name" >{{ teacher.name }} {{ teacher.surname }}
        </option>
      </select>


      <button class="btn-login btn btn-primary my-1" type="submit" >Submit</button>
    </form>
  </div> -->

<!--  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Giorno</th>
        <th scope="col">Orario</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="availability in availabilities" :key="availability.availabilityID">
        &lt;!&ndash;        <td>{{ availability.teacherId }}</td>&ndash;&gt;
        <td>{{ printTeacherName(availability.teacherId) }}</td>
        <td>{{ printSubjectName(availability.subjectId) }}</td>
        <td>{{ printDayDescription(availability.dayId) }}</td>
        <td>{{ printSlotDescription(availability.slotId) }}</td>
        <td><a class="btn-login btn btn-primary" role="button" @click="booking(availability.availabilityID)">Prenota</a>
        </td>
      </tr>

      </tbody>
    </table>
  </div>
  <span style="padding: 0 10px;"></span>-->


</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {availabilityService} from "@/Service/availabilityService";
import axios from "axios";
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";

export default {
  name: "SmartBooking",
  data() {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: {},
      days: {},
      slots: {},
      teacherSelected: this.teacherSelected,
      subjectSelected: this.subjectSelected
    };
  },
  created: async function () {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilitiesAvailable();
      let response4 = await dayService.getAllDays();
      let response5 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response.data;
      this.subjects = response2.data;
      this.availabilities = response3.data;
      this.days = response4.data;
      this.slots = response5.data;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
      console.log(this.days);
      console.log(this.slots);
    } catch (e) {
      console.log(e);
    }
  },

  methods: {
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'logout',
      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
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

    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },

    printDayDescription(Id) {
      for (let i = 0; i < this.days.length; i++) {
        if (this.days[i].id === Id) {
          console.log(this.days[i]);
          return this.days[i].description;
        }
      }
      return null;
    },

    printSlotDescription(Id) {
      for (let i = 0; i < this.slots.length; i++) {
        if (this.slots[i].id === Id) {
          console.log(this.slots[i]);
          return this.slots[i].description;
        }
      }
      return null;
    },
    booking(id) {
      console.log(id);
      const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";
      const params = {
        action: "bookingAvailability",
        availabilityId: id
      };

      if (localStorage.getItem("isLogged") === "true") {
        axios
            .get(url, {params})
            .then(response => {
              if (response.data === "booked") {
                console.log("Prenotato con successo");
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              } else {
                alert("Prenotazione fallita");
              }
            })
            .catch(error => {
              console.error(error);
            });
      }
    }
  }
};
</script>


<style scoped>

</style>