import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { ArrowLeftIcon } from '@heroicons/react/24/outline';
import { PageHeader, Card, Button, LoadingSpinner } from '../components/common';
import { bookService, categoryService } from '../services';

function AddBook() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [loadingCategories, setLoadingCategories] = useState(true);
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    isbn: '',
    publisher: '',
    edition: '',
    publicationYear: '',
    language: '',
    description: '',
    coverImageUrl: '',
    categoryId: '',
  });

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const data = await categoryService.getAll();
        setCategories(data);
      } catch (error) {
        console.error('Error fetching categories:', error);
      } finally {
        setLoadingCategories(false);
      }
    };

    fetchCategories();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.title.trim()) {
      toast.error('Title is required');
      return;
    }

    if (!formData.author.trim()) {
      toast.error('Author is required');
      return;
    }

    if (!formData.isbn.trim()) {
      toast.error('ISBN is required');
      return;
    }

    if (!formData.categoryId) {
      toast.error('Category is required');
      return;
    }

    setLoading(true);
    try {
      // Convert categoryId to integer and publicationYear if provided
      const bookData = {
        ...formData,
        categoryId: parseInt(formData.categoryId, 10),
        publicationYear: formData.publicationYear ? parseInt(formData.publicationYear, 10) : null,
      };

      await bookService.create(bookData);
      toast.success('Book added successfully');
      navigate('/books');
    } catch (error) {
      console.error('Error creating book:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <PageHeader
        title="Add New Book"
        subtitle="Add a new book to the library inventory"
        action={
          <Button variant="secondary" onClick={() => navigate('/books')}>
            <ArrowLeftIcon className="h-5 w-5 mr-2" />
            Back to Books
          </Button>
        }
      />

      <Card>
        <Card.Body>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
              {/* Title */}
              <div>
                <label htmlFor="title" className="block text-sm font-medium text-gray-700">
                  Title *
                </label>
                <input
                  type="text"
                  id="title"
                  name="title"
                  value={formData.title}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter book title"
                />
              </div>

              {/* Author */}
              <div>
                <label htmlFor="author" className="block text-sm font-medium text-gray-700">
                  Author *
                </label>
                <input
                  type="text"
                  id="author"
                  name="author"
                  value={formData.author}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter author name"
                />
              </div>

              {/* ISBN */}
              <div>
                <label htmlFor="isbn" className="block text-sm font-medium text-gray-700">
                  ISBN *
                </label>
                <input
                  type="text"
                  id="isbn"
                  name="isbn"
                  value={formData.isbn}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter ISBN"
                />
              </div>

              {/* Publisher */}
              <div>
                <label htmlFor="publisher" className="block text-sm font-medium text-gray-700">
                  Publisher
                </label>
                <input
                  type="text"
                  id="publisher"
                  name="publisher"
                  value={formData.publisher}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter publisher"
                />
              </div>

              {/* Edition */}
              <div>
                <label htmlFor="edition" className="block text-sm font-medium text-gray-700">
                  Edition
                </label>
                <input
                  type="text"
                  id="edition"
                  name="edition"
                  value={formData.edition}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="e.g., 1st Edition"
                />
              </div>

              {/* Category */}
              <div>
                <label htmlFor="categoryId" className="block text-sm font-medium text-gray-700">
                  Category *
                </label>
                {loadingCategories ? (
                  <LoadingSpinner size="sm" />
                ) : (
                  <select
                    id="categoryId"
                    name="categoryId"
                    value={formData.categoryId}
                    onChange={handleChange}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  >
                    <option value="">Select a category</option>
                    {categories.map((category) => (
                      <option key={category.id} value={category.id}>
                        {category.name}
                      </option>
                    ))}
                  </select>
                )}
              </div>

              {/* Publication Year */}
              <div>
                <label htmlFor="publicationYear" className="block text-sm font-medium text-gray-700">
                  Publication Year
                </label>
                <input
                  type="number"
                  id="publicationYear"
                  name="publicationYear"
                  value={formData.publicationYear}
                  onChange={handleChange}
                  min="1800"
                  max={new Date().getFullYear()}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="e.g., 2024"
                />
              </div>

              {/* Language */}
              <div>
                <label htmlFor="language" className="block text-sm font-medium text-gray-700">
                  Language
                </label>
                <input
                  type="text"
                  id="language"
                  name="language"
                  value={formData.language}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="e.g., English"
                />
              </div>

              {/* Cover Image URL */}
              <div className="sm:col-span-2">
                <label htmlFor="coverImageUrl" className="block text-sm font-medium text-gray-700">
                  Cover Image URL
                </label>
                <input
                  type="url"
                  id="coverImageUrl"
                  name="coverImageUrl"
                  value={formData.coverImageUrl}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="https://example.com/cover.jpg"
                />
              </div>

              {/* Description */}
              <div className="sm:col-span-2">
                <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                  Description
                </label>
                <textarea
                  id="description"
                  name="description"
                  rows={4}
                  value={formData.description}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter book description"
                />
              </div>
            </div>

            <div className="flex justify-end gap-3">
              <Button type="button" variant="secondary" onClick={() => navigate('/books')}>
                Cancel
              </Button>
              <Button type="submit" loading={loading}>
                Add Book
              </Button>
            </div>
          </form>
        </Card.Body>
      </Card>
    </div>
  );
}

export default AddBook;
