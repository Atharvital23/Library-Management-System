import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { BookOpenIcon, UsersIcon, ArrowsRightLeftIcon, TagIcon } from '@heroicons/react/24/outline';
import { PageHeader, Card, LoadingSpinner } from '../components/common';
import { bookService, studentService, categoryService } from '../services';

function Dashboard() {
  const [stats, setStats] = useState({
    books: 0,
    students: 0,
    categories: 0,
  });
  const [loading, setLoading] = useState(true);
  const [recentBooks, setRecentBooks] = useState([]);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [books, students, categories] = await Promise.all([
          bookService.getAll(),
          studentService.getAll(),
          categoryService.getAll(),
        ]);

        setStats({
          books: books.length,
          students: students.length,
          categories: categories.length,
        });

        // Get recent books (last 5)
        setRecentBooks(books.slice(-5).reverse());
      } catch (error) {
        console.error('Error fetching stats:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  const statCards = [
    { name: 'Total Books', value: stats.books, icon: BookOpenIcon, href: '/books', color: 'bg-blue-500' },
    { name: 'Students', value: stats.students, icon: UsersIcon, href: '/students', color: 'bg-green-500' },
    { name: 'Categories', value: stats.categories, icon: TagIcon, href: '/categories', color: 'bg-purple-500' },
    { name: 'Circulation', value: 'Manage', icon: ArrowsRightLeftIcon, href: '/circulation', color: 'bg-orange-500' },
  ];

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
        title="Dashboard" 
        subtitle="Welcome to the Library Management System"
      />

      {/* Stats Grid */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8">
        {statCards.map((stat) => (
          <Link key={stat.name} to={stat.href}>
            <Card className="hover:shadow-md transition-shadow">
              <Card.Body>
                <div className="flex items-center">
                  <div className={`${stat.color} p-3 rounded-lg`}>
                    <stat.icon className="h-6 w-6 text-white" />
                  </div>
                  <div className="ml-4">
                    <p className="text-sm font-medium text-gray-500">{stat.name}</p>
                    <p className="text-2xl font-semibold text-gray-900">{stat.value}</p>
                  </div>
                </div>
              </Card.Body>
            </Card>
          </Link>
        ))}
      </div>

      {/* Recent Books */}
      <Card>
        <Card.Header>
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-medium text-gray-900">Recent Books</h3>
            <Link to="/books" className="text-sm font-medium text-indigo-600 hover:text-indigo-500">
              View all
            </Link>
          </div>
        </Card.Header>
        <Card.Body className="p-0">
          <div className="divide-y divide-gray-200">
            {recentBooks.length === 0 ? (
              <p className="p-6 text-gray-500 text-center">No books found. Add your first book!</p>
            ) : (
              recentBooks.map((book) => (
                <Link
                  key={book.id}
                  to={`/books/${book.id}`}
                  className="flex items-center px-6 py-4 hover:bg-gray-50"
                >
                  <div className="shrink-0 w-12 h-16 bg-gray-100 rounded overflow-hidden">
                    {book.coverImageUrl ? (
                      <img src={book.coverImageUrl} alt={book.title} className="w-full h-full object-cover" />
                    ) : (
                      <div className="w-full h-full flex items-center justify-center">
                        <BookOpenIcon className="h-6 w-6 text-gray-400" />
                      </div>
                    )}
                  </div>
                  <div className="ml-4 flex-1">
                    <p className="text-sm font-medium text-gray-900">{book.title}</p>
                    <p className="text-sm text-gray-500">{book.author}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm text-gray-900">{book.availableCopies || 0} available</p>
                  </div>
                </Link>
              ))
            )}
          </div>
        </Card.Body>
      </Card>
    </div>
  );
}

export default Dashboard;
