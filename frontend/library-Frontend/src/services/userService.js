import api from '../config/axiosConfig';

const userService = {
  // Register a new admin/librarian
  register: async (userData) => {
    const response = await api.post('/users/register', userData);
    return response.data;
  },
};

export default userService;
