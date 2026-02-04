import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { PlusIcon, BookOpenIcon, EyeIcon } from '@heroicons/react/24/outline';
import { PageHeader, Card, Button, LoadingSpinner } from '../components/common';
import { bookService } from '../services';

function BookList() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const data = await bookService.getAll();
      setBooks(data);
    } catch (error) {
      console.error('Error fetching books:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredBooks = books.filter(
    (book) =>
      book.title?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.author?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.isbn?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-96">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  return (
    <div>
      <PageHeader
        title="Books Inventory"
        subtitle="Manage your library's book collection"
        action={
          <Link to="/books/add">
            <Button>
              <PlusIcon className="h-5 w-5 mr-2" />
              Add Book
            </Button>
          </Link>
        }
      />

      {/* Search Bar */}
      <div className="mb-6">
        <input
          type="text"
          placeholder="Search by title, author, or ISBN..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-96 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-4 py-2"
        />
      </div>

      {/* Books Grid */}
      {filteredBooks.length === 0 ? (
        <Card>
          <Card.Body className="text-center py-12">
            <BookOpenIcon className="mx-auto h-12 w-12 text-gray-400" />
            <h3 className="mt-2 text-sm font-medium text-gray-900">No books found</h3>
            <p className="mt-1 text-sm text-gray-500">
              {searchTerm ? 'Try a different search term.' : 'Get started by adding a new book.'}
            </p>
            {!searchTerm && (
              <div className="mt-6">
                <Link to="/books/add">
                  <Button>
                    <PlusIcon className="h-5 w-5 mr-2" />
                    Add Book
                  </Button>
                </Link>
              </div>
            )}
          </Card.Body>
        </Card>
      ) : (
        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {filteredBooks.map((book) => (
            <Card key={book.id} className="hover:shadow-lg transition-shadow">
              <div className="aspect-3/4 bg-gray-100 rounded-t-lg overflow-hidden">
                {book.coverImageUrl ? (
                  <img
                    src={book.coverImageUrl}
                    alt={book.title}
                    className="w-full h-full object-cover"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center">
                    <BookOpenIcon className="h-16 w-16 text-gray-400" />
                  </div>
                )}
              </div>
              <Card.Body>
                <h3 className="text-lg font-semibold text-gray-900 truncate">{book.title}</h3>
                <p className="text-sm text-gray-600 mt-1">{book.author}</p>
                <p className="text-xs text-gray-500 mt-1">ISBN: {book.isbn}</p>
                <div className="flex items-center justify-between mt-4">
                  <span className={`text-sm font-medium ${
                    book.availableCopies > 0 ? 'text-green-600' : 'text-red-600'
                  }`}>
                    {book.availableCopies || 0} available
                  </span>
                  <Link to={`/books/${book.id}`}>
                    <Button variant="secondary" size="sm">
                      <EyeIcon className="h-4 w-4 mr-1" />
                      View
                    </Button>
                  </Link>
                </div>
              </Card.Body>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}

export default BookList;
