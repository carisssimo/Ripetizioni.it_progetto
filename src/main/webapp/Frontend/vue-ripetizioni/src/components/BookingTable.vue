<template>
  <!--  nuova tabella -->

  <div class="form-container-selector container ">
    <div class="title-main">3. Seleziona Ora lo Slot orario!</div>
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
          <div  v-if="existAvailability(day.id, slot.id)==='true'" >
          <a class="btn-login btn btn-primary" role="button" @click="booking(searchAvailability(day.id, slot.id))">Prenota</a>
          </div>
        </td>
      </tr>
      </tbody>

    </table>
  </div>
</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {availabilityService} from "@/Service/availabilityService";
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";
import $ from 'jquery';

export default {
  name: "BookingTable",
  data() {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: {},
      days: {},
      slots: {},
      teacherSelected: '',
      subjectSelected: '',
    };
  },
  created: async function () {
    try {
      this.loading = true;
      this.subjectSelected=await subjectsService.getSubjectId(this.$route.params.subjectName);
      this.teacherSelected= this.$route.params.teacherId;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilitiesByProfessor(this.teacherSelected);
      let response4 = await dayService.getAllDays();
      let response5 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response;
      this.subjects = response2;
      this.availabilities = response3;
      this.days = response4;
      this.slots = response5;

      console.log(this.availabilities);
      console.log(this.subjectSelected);
      console.log(this.teacherSelected);

    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    getProfessor(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].teacherId;
        }
      }

    },
    getSubject(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].subjectId;
        }
      }

    },
    getState(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].booking;
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

    createId(dayId, slotId) {
      console.log(dayId + " " + slotId)
      return dayId + " " + slotId
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


    booking(id) {
      console.log(id)
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'bookingAvailability',
        availabilityId: id,
        subjectId:this.subjectSelected,
      };


      if (localStorage.getItem("isLogged") === "true") {  //controllo se è effettivamente un utente loggato
        $.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "booked") {
                console.log(" prenotato con successo ")
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              } else {
                alert("prenotazione fallita ");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }


    },
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
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
    searchAvailability(day, slot) {
      console.log(day)
      console.log(slot)
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          return this.availabilities[i].availabilityID
        }
      }
    },
  }
  }
</script>

<style scoped>

</style>