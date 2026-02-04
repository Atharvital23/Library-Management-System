import api from '../config/axiosConfig';

const categoryService = {
  // Get all categories
  getAll: async () => {
    const response = await api.get('/categories');
    return response.data;
  },

  // Create a new category
  create: async (categoryData) => {
    const response = await api.post('/categories', categoryData);
    return response.data;
  },

  // Delete a category
  delete: async (id) => {
    const response = await api.delete(`/categories/${id}`);
    return response.data;
  },
};

export default categoryService;
