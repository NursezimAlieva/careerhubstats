<template>
  <div class="dashboard">
    <h1>Статистика CareerHub</h1>

    <div class="stats-container">
      <div class="card"><h3>Пользователи</h3><p class="number">{{ stats.totalUsers }}</p></div>
      <div class="card"><h3>Студенты</h3><p class="number highlight">{{ stats.totalStudents }}</p></div>
      <div class="card"><h3>Компании</h3><p class="number blue-text">{{ stats.totalCompanies }}</p></div>
    </div>

    <div class="charts-grid">
      <div class="chart-box">
        <h3>Регистрации</h3>
        <RegistrationChart v-if="stats.chartLabels.length > 0" :labels="stats.chartLabels" :studentData="stats.studentData" :companyData="stats.companyData" />
      </div>
      <div class="chart-box">
        <h3>Стажировки</h3>
        <RegistrationChart v-if="stats.chartLabels.length > 0" :labels="stats.chartLabels" :studentData="stats.internshipData" :companyData="[]" />
      </div>
      <div class="chart-box">
        <h3>Работа</h3>
        <RegistrationChart v-if="stats.chartLabels.length > 0" :labels="stats.chartLabels" :studentData="stats.jobData" :companyData="[]" />
      </div>
    </div>

    <div class="management-section">
      <h3>Управление кандидатами</h3>
      <button @click="generatePDF" class="pdf-btn">📄 Скачать PDF отчет</button>
      <table class="user-table">
        <thead>
        <tr>
          <th>ID</th>
          <th>Имя</th>
          <th>Статус</th>
          <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in allUsers" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.name }}</td>
          <td>
            <span :class="getStatusClass(user)">{{ getStatusText(user) }}</span>
          </td>
          <td>
            <button v-if="user.role === 'student' && !user.intern" @click="acceptToInternship(user.id)" class="action-btn intern-btn">На стажировку</button>
            <button v-if="user.role === 'student' && user.intern && !user.job" @click="acceptToJob(user.id)" class="action-btn job-btn">На работу</button>
            <span v-else-if="user.role === 'company'">—</span>
            <span v-else-if="user.job">✅ Трудоустроен</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="add-user-section">
      <h3>Регистрация</h3>
      <input v-model="newUserName" placeholder="Имя..." @keyup.enter="addUser('student')" />
      <div class="button-group">
        <button @click="addUser('student')" class="add-btn student-btn">+ Студент</button>
        <button @click="addUser('company')" class="add-btn company-btn">+ Компания</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from './api';
import RegistrationChart from './components/RegistrationChart.vue';
import jsPDF from 'jspdf';
import 'jspdf-autotable';

const stats = ref({
  totalUsers: 0, totalStudents: 0, totalCompanies: 0,
  chartLabels: [], studentData: [], companyData: [], internshipData: [], jobData: []
});

const allUsers = ref([]);
const newUserName = ref('');

const fetchData = async () => {
  try {
    const statRes = await api.get('');
    stats.value = statRes.data;
    const usersRes = await api.get('/users');
    allUsers.value = usersRes.data;
  } catch (error) {
    console.error("Ошибка загрузки:", error);
  }
};

// ТЕ САМЫЕ ФУНКЦИИ, КОТОРЫХ НЕ ХВАТАЛО:
const getStatusText = (user) => {
  if (user.job) return 'Работает';
  if (user.intern) return 'Стажер';
  return 'В поиске';
};

const getStatusClass = (user) => {
  if (user.job) return 'status-job';
  if (user.intern) return 'status-ready';
  return 'status-wait';
};

const acceptToInternship = async (id) => {
  try {
    await api.put(`/intern/${id}`);
    await fetchData();
  } catch (e) { console.error(e); }
};

const acceptToJob = async (id) => {
  try {
    await api.put(`/job/${id}`);
    await fetchData();
  } catch (e) { console.error(e); }
};

const addUser = async (role) => {
  if (!newUserName.value) return;
  try {
    await api.post('/users', { name: newUserName.value, role: role });
    newUserName.value = '';
    await fetchData();
  } catch (e) { console.error(e); }
};

const generatePDF = () => {
  const doc = new jsPDF();

  // Заголовок отчета
  doc.setFontSize(18);
  doc.text('CareerHub: Отчет по кандидатам', 14, 20);
  doc.setFontSize(11);
  doc.text(`Дата формирования: ${new Date().toLocaleDateString()}`, 14, 30);

  // Формируем данные для таблицы
  const tableColumn = ["ID", "Имя", "Роль", "Статус"];
  const tableRows = allUsers.value.map(user => [
    user.id,
    user.name,
    user.role === 'student' ? 'Студент' : 'Компания',
    getStatusText(user)
  ]);

  // Генерируем таблицу в PDF
  doc.autoTable({
    head: [tableColumn],
    body: tableRows,
    startY: 40,
    theme: 'grid',
    headStyles: { fillColor: [66, 184, 131] } // Фирменный зеленый цвет
  });

  // Сохраняем файл
  doc.save(`CareerHub_Report_${new Date().toISOString().slice(0,10)}.pdf`);
};

onMounted(fetchData);
</script>

<style scoped>
.dashboard { font-family: sans-serif; text-align: center; padding: 20px; color: #fff; background: #1a1a1a; min-height: 100vh; }
.stats-container { display: flex; justify-content: center; gap: 15px; margin-bottom: 30px; }
.card { background: #2a2a2a; padding: 15px; border-radius: 10px; border: 1px solid #444; min-width: 150px; }
.charts-grid { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; }
.chart-box { background: #252525; padding: 15px; border-radius: 12px; width: 30%; min-width: 300px; }
.management-section { margin-top: 30px; background: #252525; padding: 20px; border-radius: 12px; }
.user-table { width: 100%; border-collapse: collapse; }
.user-table th, .user-table td { padding: 10px; border-bottom: 1px solid #333; text-align: left; }
.status-wait { color: #f1c40f; }
.status-ready { color: #3498db; font-weight: bold; }
.status-job { color: #42b883; font-weight: bold; }
.action-btn { border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer; color: white; margin-right: 5px; }
.intern-btn { background: #3498db; }
.job-btn { background: #42b883; }
.add-user-section { margin-top: 30px; }
input { padding: 10px; border-radius: 5px; border: 1px solid #444; background: #000; color: #fff; margin-bottom: 10px; }
.add-btn { padding: 10px 20px; border: none; border-radius: 5px; color: white; cursor: pointer; margin: 0 5px; }
.student-btn { background: #42b883; }
.company-btn { background: #3498db; }
.management-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.pdf-btn {
  background: #e74c3c;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  transition: 0.3s;
}

.pdf-btn:hover {
  background: #c0392b;
  transform: translateY(-2px);
}
</style>