import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { ArrowLeftIcon, BookOpenIcon, PlusIcon } from '@heroicons/react/24/outline';
import { PageHeader, Card, Button, LoadingSpinner, StatusBadge, Modal } from '../components/common';
import { bookService, copyService } from '../services';

function BookDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [copies, setCopies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newCopy, setNewCopy] = useState({ qrCodeStr: '', shelfLocation: '' });
  const [submitting, setSubmitting] = useState(false);

  const fetchBookDetails = async () => {
    try {
      const [bookData, copiesData] = await Promise.all([
        bookService.getById(id),
        copyService.getByBookId(id),
      ]);
      console.log('Book data:', bookData);
      console.log('Copies data:', copiesData);
      setBook(bookData);
      setCopies(copiesData);
    } catch (error) {
      console.error('Error fetching book details:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBookDetails();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  const handleAddCopy = async (e) => {
    e.preventDefault();

    if (!newCopy.qrCodeStr.trim()) {
      toast.error('QR Code is required');
      return;
    }

    if (!newCopy.shelfLocation.trim()) {
      toast.error('Shelf location is required');
      return;
    }

    setSubmitting(true);
    try {
      await copyService.add(id, newCopy.qrCodeStr, newCopy.shelfLocation);
      toast.success('Copy added successfully');
      setIsModalOpen(false);
      setNewCopy({ qrCodeStr: '', shelfLocation: '' });
      fetchBookDetails();
    } catch (error) {
      console.error('Error adding copy:', error);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-96">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  if (!book) {
    return (
      <Card>
        <Card.Body className="text-center py-12">
          <h3 className="text-lg font-medium text-gray-900">Book not found</h3>
          <Button variant="secondary" onClick={() => navigate('/books')} className="mt-4">
            Back to Books
          </Button>
        </Card.Body>
      </Card>
    );
  }

  return (
    <div>
      <PageHeader
        title={book.title}
        subtitle={`by ${book.author}`}
        action={
          <Button variant="secondary" onClick={() => navigate('/books')}>
            <ArrowLeftIcon className="h-5 w-5 mr-2" />
            Back to Books
          </Button>
        }
      />

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Book Info Card */}
        <div className="lg:col-span-1">
          <Card>
            <div className="aspect-3/4 bg-gray-100 rounded-t-lg overflow-hidden">
              {book.coverImageUrl ? (
                <img
                  src={book.coverImageUrl}
                  alt={book.title}
                  className="w-full h-full object-cover"
                />
              ) : (
                <div className="w-full h-full flex items-center justify-center">
                  <BookOpenIcon className="h-24 w-24 text-gray-400" />
                </div>
              )}
            </div>
            <Card.Body>
              <dl className="space-y-3">
                <div>
                  <dt className="text-sm font-medium text-gray-500">ISBN</dt>
                  <dd className="text-sm text-gray-900">{book.isbn}</dd>
                </div>
                <div>
                  <dt className="text-sm font-medium text-gray-500">Publisher</dt>
                  <dd className="text-sm text-gray-900">{book.publisher || '-'}</dd>
                </div>
                <div>
                  <dt className="text-sm font-medium text-gray-500">Edition</dt>
                  <dd className="text-sm text-gray-900">{book.edition || '-'}</dd>
                </div>
                <div>
                  <dt className="text-sm font-medium text-gray-500">Category</dt>
                  <dd className="text-sm text-gray-900">{book.category?.name || '-'}</dd>
                </div>
                <div>
                  <dt className="text-sm font-medium text-gray-500">Available Copies</dt>
                  <dd className={`text-sm font-semibold ${
                    book.availableCopies > 0 ? 'text-green-600' : 'text-red-600'
                  }`}>
                    {book.availableCopies || 0}
                  </dd>
                </div>
              </dl>
            </Card.Body>
          </Card>
        </div>

        {/* Details and Copies */}
        <div className="lg:col-span-2 space-y-6">
          {/* Description */}
          {book.description && (
            <Card>
              <Card.Header>
                <h3 className="text-lg font-medium text-gray-900">Description</h3>
              </Card.Header>
              <Card.Body>
                <p className="text-gray-600">{book.description}</p>
              </Card.Body>
            </Card>
          )}

          {/* Physical Copies */}
          <Card>
            <Card.Header>
              <div className="flex items-center justify-between">
                <h3 className="text-lg font-medium text-gray-900">
                  Physical Copies ({copies.length})
                </h3>
                <Button size="sm" onClick={() => setIsModalOpen(true)}>
                  <PlusIcon className="h-4 w-4 mr-1" />
                  Add Copy
                </Button>
              </div>
            </Card.Header>
            <Card.Body className="p-0">
              {copies.length === 0 ? (
                <div className="p-6 text-center text-gray-500">
                  No physical copies found. Add your first copy!
                </div>
              ) : (
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        QR Code
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Shelf
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Status
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {copies.map((copy) => (
                      <tr key={copy.id} className="hover:bg-gray-50">
                        <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-gray-900">
                          {copy.qrCodeStr}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                          {copy.shelfLocation}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <StatusBadge status={copy.status} type="copy" />
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </Card.Body>
          </Card>
        </div>
      </div>

      {/* Add Copy Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        title="Add Physical Copy"
      >
        <form onSubmit={handleAddCopy}>
          <div className="space-y-4">
            <div>
              <label htmlFor="qrCodeStr" className="block text-sm font-medium text-gray-700">
                QR Code *
              </label>
              <input
                type="text"
                id="qrCodeStr"
                value={newCopy.qrCodeStr}
                onChange={(e) => setNewCopy({ ...newCopy, qrCodeStr: e.target.value })}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                placeholder="Scan or enter QR code"
              />
            </div>
            <div>
              <label htmlFor="shelfLocation" className="block text-sm font-medium text-gray-700">
                Shelf Location *
              </label>
              <input
                type="text"
                id="shelfLocation"
                value={newCopy.shelfLocation}
                onChange={(e) => setNewCopy({ ...newCopy, shelfLocation: e.target.value })}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                placeholder="e.g., A-12"
              />
            </div>
          </div>
          <div className="mt-6 flex justify-end gap-3">
            <Button
              type="button"
              variant="secondary"
              onClick={() => setIsModalOpen(false)}
            >
              Cancel
            </Button>
            <Button type="submit" loading={submitting}>
              Add Copy
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}

export default BookDetails;
