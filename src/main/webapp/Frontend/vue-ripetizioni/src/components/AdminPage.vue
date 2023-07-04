<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

      <div style="margin-right: 25px;"><RouterLink to="/"><a class="btn-login btn btn-primary" @click="Click" role="button">logout</a></RouterLink></div>
    </form>
  </nav>

  <div class="logged-container title-container container">
    <h1 class="title-main">Pagina Amministratore</h1>
  <h1 class="title-main">Bentornato!</h1>
  <p class="p-start"></p>
  <RouterLink to="/AddProf"><a class="btn-login btn btn-primary" role="button">+ Nuovo Docente</a></RouterLink>
  <span style="padding: 0 10px;"></span>
  <RouterLink to="/Addsub"><a class="btn-l btn-login btn btn-success" role="button">+ Nuovo Corso</a></RouterLink>
  <span style="padding: 0 10px;"></span>
  <RouterLink to="/DelProf"><a class="btn-l btn-login btn btn-success" role="button" > - Elimina Docente</a></RouterLink>
  <span style="padding: 0 10px;"></span>
  <RouterLink to="/update"><a class="btn-l btn-login btn btn-success" role="button" > - Elimina Corso</a></RouterLink>
  </div>




</template>

<script>
import { teacherService } from "@/Service/teachersService";
import { subjectsService } from "@/Service/subjectsService";
import { availabilityService } from "@/Service/availabilityService";
import axios from "axios";

export default {
  name: "AdminPage",
  data() {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: {},
      teacherSelected: this.teacherSelected,
      subjectSelected: this.subjectSelected
    };
  },
  created: async function() {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilitiesAvailable();
      this.loading = false;
      this.teachers = response.data;
      this.subjects = response2.data;
      this.availabilities = response3.data;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
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
    booking(id) {
      console.log(id);
      const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";
      const params = {
        action: "bookingAvailability",
        availabilityId: id
      };

      axios
          .get(url, { params })
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
};
</script>


<style scoped>

</style>