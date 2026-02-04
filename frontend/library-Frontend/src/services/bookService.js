import api from '../config/axiosConfig';

const bookService = {
  // Get all books
  getAll: async () => {
    const response = await api.get('/books');
    return response.data;
  },

  // Get a single book by ID
  getById: async (id) => {
    const response = await api.get(`/books/${id}`);
    return response.data;
  },

  // Create a new book
  create: async (bookData) => {
    const response = await api.post('/books', bookData);
    return response.data;
  },

  // Update a book
  update: async (id, bookData) => {
    const response = await api.put(`/books/${id}`, bookData);
    return response.data;
  },

  // Delete a book
  delete: async (id) => {
    const response = await api.delete(`/books/${id}`);
    return response.data;
  },
};

export default bookService;
