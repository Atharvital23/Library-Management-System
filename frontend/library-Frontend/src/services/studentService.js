import api from '../config/axiosConfig';

const studentService = {
  // Get all students
  getAll: async () => {
    const response = await api.get('/students');
    return response.data;
  },

  // Get a student by ID card
  getByCardId: async (cardId) => {
    const response = await api.get(`/students/${cardId}`);
    return response.data;
  },

  // Register a new student
  register: async (studentData) => {
    const response = await api.post('/students', studentData);
    return response.data;
  },
};

export default studentService;
