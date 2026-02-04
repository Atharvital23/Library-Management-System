import { useState } from 'react';
import { toast } from 'react-toastify';
import { 
  ArrowRightStartOnRectangleIcon, 
  ArrowLeftStartOnRectangleIcon,
  ArrowPathIcon,
  CurrencyDollarIcon,
  QrCodeIcon
} from '@heroicons/react/24/outline';
import { PageHeader, Card, Button, StatusBadge } from '../components/common';
import { transactionService } from '../services';

function Circulation() {
  const [activeTab, setActiveTab] = useState('issue');
  
  // Issue form state
  const [issueData, setIssueData] = useState({
    qrCodeStr: '',
    studentIdCard: '',
    issuedBy: 'Admin',
  });
  const [issuing, setIssuing] = useState(false);
  const [issueResult, setIssueResult] = useState(null);

  // Return form state
  const [returnQrCode, setReturnQrCode] = useState('');
  const [returning, setReturning] = useState(false);
  const [returnResult, setReturnResult] = useState(null);

  // Renew form state
  const [renewTransactionId, setRenewTransactionId] = useState('');
  const [renewing, setRenewing] = useState(false);
  const [renewResult, setRenewResult] = useState(null);

  // Pay Fine form state
  const [fineTransactionId, setFineTransactionId] = useState('');
  const [payingFine, setPayingFine] = useState(false);
  const [fineResult, setFineResult] = useState(null);

  const handleIssue = async (e) => {
    e.preventDefault();

    if (!issueData.qrCodeStr.trim()) {
      toast.error('QR Code is required');
      return;
    }

    if (!issueData.studentIdCard.trim()) {
      toast.error('Student ID Card is required');
      return;
    }

    setIssuing(true);
    setIssueResult(null);
    try {
      const result = await transactionService.issue(
        issueData.qrCodeStr,
        issueData.studentIdCard,
        issueData.issuedBy
      );
      setIssueResult(result);
      toast.success('Book issued successfully!');
      setIssueData({ qrCodeStr: '', studentIdCard: '', issuedBy: 'Admin' });
    } catch (error) {
      console.error('Error issuing book:', error);
    } finally {
      setIssuing(false);
    }
  };

  const handleReturn = async (e) => {
    e.preventDefault();

    if (!returnQrCode.trim()) {
      toast.error('QR Code is required');
      return;
    }

    setReturning(true);
    setReturnResult(null);
    try {
      const result = await transactionService.return(returnQrCode);
      setReturnResult(result);
      toast.success('Book returned successfully!');
      setReturnQrCode('');
    } catch (error) {
      console.error('Error returning book:', error);
    } finally {
      setReturning(false);
    }
  };

  const handleRenew = async (e) => {
    e.preventDefault();

    if (!renewTransactionId.trim()) {
      toast.error('Transaction ID is required');
      return;
    }

    setRenewing(true);
    setRenewResult(null);
    try {
      const result = await transactionService.renew(renewTransactionId);
      setRenewResult(result);
      toast.success('Book renewed successfully!');
      setRenewTransactionId('');
    } catch (error) {
      console.error('Error renewing book:', error);
    } finally {
      setRenewing(false);
    }
  };

  const handlePayFine = async (e) => {
    e.preventDefault();

    if (!fineTransactionId.trim()) {
      toast.error('Transaction ID is required');
      return;
    }

    setPayingFine(true);
    setFineResult(null);
    try {
      const result = await transactionService.payFine(fineTransactionId);
      setFineResult(result);
      toast.success('Fine paid successfully!');
      setFineTransactionId('');
    } catch (error) {
      console.error('Error paying fine:', error);
    } finally {
      setPayingFine(false);
    }
  };

  const tabs = [
    { id: 'issue', name: 'Issue Book', icon: ArrowRightStartOnRectangleIcon },
    { id: 'return', name: 'Return Book', icon: ArrowLeftStartOnRectangleIcon },
    { id: 'renew', name: 'Renew Book', icon: ArrowPathIcon },
    { id: 'fine', name: 'Pay Fine', icon: CurrencyDollarIcon },
  ];

  const formatDate = (dateString) => {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString();
  };

  return (
    <div>
      <PageHeader
        title="Circulation Desk"
        subtitle="Issue, return, renew books and manage fines"
      />

      {/* Tabs */}
      <div className="border-b border-gray-200 mb-6">
        <nav className="-mb-px flex space-x-8">
          {tabs.map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`
                group inline-flex items-center border-b-2 py-4 px-1 text-sm font-medium
                ${activeTab === tab.id
                  ? 'border-indigo-500 text-indigo-600'
                  : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700'
                }
              `}
            >
              <tab.icon
                className={`-ml-0.5 mr-2 h-5 w-5 ${
                  activeTab === tab.id ? 'text-indigo-500' : 'text-gray-400 group-hover:text-gray-500'
                }`}
              />
              {tab.name}
            </button>
          ))}
        </nav>
      </div>

      {/* Issue Panel */}
      {activeTab === 'issue' && (
        <Card>
          <Card.Header>
            <h3 className="text-lg font-medium text-gray-900">Issue Book</h3>
            <p className="mt-1 text-sm text-gray-500">Scan the book QR code and enter student ID to issue</p>
          </Card.Header>
          <Card.Body>
            <form onSubmit={handleIssue} className="space-y-4 max-w-lg">
              <div>
                <label htmlFor="qrCode" className="block text-sm font-medium text-gray-700">
                  Book QR Code *
                </label>
                <div className="mt-1 relative">
                  <input
                    type="text"
                    id="qrCode"
                    value={issueData.qrCodeStr}
                    onChange={(e) => setIssueData({ ...issueData, qrCodeStr: e.target.value })}
                    className="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border pl-10 py-2 pr-4"
                    placeholder="Scan or enter QR code"
                  />
                  <QrCodeIcon className="h-5 w-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />
                </div>
              </div>

              <div>
                <label htmlFor="studentId" className="block text-sm font-medium text-gray-700">
                  Student ID Card *
                </label>
                <input
                  type="text"
                  id="studentId"
                  value={issueData.studentIdCard}
                  onChange={(e) => setIssueData({ ...issueData, studentIdCard: e.target.value })}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter student ID card"
                />
              </div>

              <div>
                <label htmlFor="issuedBy" className="block text-sm font-medium text-gray-700">
                  Issued By
                </label>
                <input
                  type="text"
                  id="issuedBy"
                  value={issueData.issuedBy}
                  onChange={(e) => setIssueData({ ...issueData, issuedBy: e.target.value })}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Admin name"
                />
              </div>

              <Button type="submit" loading={issuing}>
                <ArrowRightStartOnRectangleIcon className="h-5 w-5 mr-2" />
                Issue Book
              </Button>
            </form>

            {/* Issue Result */}
            {issueResult && (
              <div className="mt-6 p-4 bg-green-50 rounded-lg border border-green-200">
                <h4 className="font-medium text-green-800">Book Issued Successfully!</h4>
                <div className="mt-2 grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <span className="text-gray-500">Transaction ID:</span>
                    <p className="font-medium">{issueResult.id}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">Due Date:</span>
                    <p className="font-medium">{formatDate(issueResult.dueDate)}</p>
                  </div>
                </div>
              </div>
            )}
          </Card.Body>
        </Card>
      )}

      {/* Return Panel */}
      {activeTab === 'return' && (
        <Card>
          <Card.Header>
            <h3 className="text-lg font-medium text-gray-900">Return Book</h3>
            <p className="mt-1 text-sm text-gray-500">Scan the book QR code to process return</p>
          </Card.Header>
          <Card.Body>
            <form onSubmit={handleReturn} className="space-y-4 max-w-lg">
              <div>
                <label htmlFor="returnQrCode" className="block text-sm font-medium text-gray-700">
                  Book QR Code *
                </label>
                <div className="mt-1 relative">
                  <input
                    type="text"
                    id="returnQrCode"
                    value={returnQrCode}
                    onChange={(e) => setReturnQrCode(e.target.value)}
                    className="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border pl-10 py-2 pr-4"
                    placeholder="Scan or enter QR code"
                  />
                  <QrCodeIcon className="h-5 w-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />
                </div>
              </div>

              <Button type="submit" loading={returning} variant="success">
                <ArrowLeftStartOnRectangleIcon className="h-5 w-5 mr-2" />
                Return Book
              </Button>
            </form>

            {/* Return Result */}
            {returnResult && (
              <div className="mt-6 p-4 bg-green-50 rounded-lg border border-green-200">
                <h4 className="font-medium text-green-800">Book Returned Successfully!</h4>
                <div className="mt-2 grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <span className="text-gray-500">Transaction ID:</span>
                    <p className="font-medium">{returnResult.id}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">Return Date:</span>
                    <p className="font-medium">{formatDate(returnResult.returnDate)}</p>
                  </div>
                  {returnResult.fineAmount > 0 && (
                    <div className="col-span-2">
                      <span className="text-gray-500">Fine Amount:</span>
                      <p className="font-bold text-red-600">${returnResult.fineAmount?.toFixed(2)}</p>
                    </div>
                  )}
                </div>
              </div>
            )}
          </Card.Body>
        </Card>
      )}

      {/* Renew Panel */}
      {activeTab === 'renew' && (
        <Card>
          <Card.Header>
            <h3 className="text-lg font-medium text-gray-900">Renew Book</h3>
            <p className="mt-1 text-sm text-gray-500">Enter the transaction ID to renew the book</p>
          </Card.Header>
          <Card.Body>
            <form onSubmit={handleRenew} className="space-y-4 max-w-lg">
              <div>
                <label htmlFor="renewTransactionId" className="block text-sm font-medium text-gray-700">
                  Transaction ID *
                </label>
                <input
                  type="number"
                  id="renewTransactionId"
                  value={renewTransactionId}
                  onChange={(e) => setRenewTransactionId(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter transaction ID"
                />
              </div>

              <Button type="submit" loading={renewing}>
                <ArrowPathIcon className="h-5 w-5 mr-2" />
                Renew Book
              </Button>
            </form>

            {/* Renew Result */}
            {renewResult && (
              <div className="mt-6 p-4 bg-blue-50 rounded-lg border border-blue-200">
                <h4 className="font-medium text-blue-800">Book Renewed Successfully!</h4>
                <div className="mt-2 grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <span className="text-gray-500">Transaction ID:</span>
                    <p className="font-medium">{renewResult.id}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">New Due Date:</span>
                    <p className="font-medium">{formatDate(renewResult.dueDate)}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">Renewal Count:</span>
                    <p className="font-medium">{renewResult.renewalCount || 1}</p>
                  </div>
                </div>
              </div>
            )}
          </Card.Body>
        </Card>
      )}

      {/* Pay Fine Panel */}
      {activeTab === 'fine' && (
        <Card>
          <Card.Header>
            <h3 className="text-lg font-medium text-gray-900">Pay Fine</h3>
            <p className="mt-1 text-sm text-gray-500">Enter the transaction ID to pay the fine</p>
          </Card.Header>
          <Card.Body>
            <form onSubmit={handlePayFine} className="space-y-4 max-w-lg">
              <div>
                <label htmlFor="fineTransactionId" className="block text-sm font-medium text-gray-700">
                  Transaction ID *
                </label>
                <input
                  type="number"
                  id="fineTransactionId"
                  value={fineTransactionId}
                  onChange={(e) => setFineTransactionId(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                  placeholder="Enter transaction ID"
                />
              </div>

              <Button type="submit" loading={payingFine} variant="success">
                <CurrencyDollarIcon className="h-5 w-5 mr-2" />
                Pay Fine
              </Button>
            </form>

            {/* Fine Payment Result */}
            {fineResult && (
              <div className="mt-6 p-4 bg-green-50 rounded-lg border border-green-200">
                <h4 className="font-medium text-green-800">Fine Paid Successfully!</h4>
                <div className="mt-2 grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <span className="text-gray-500">Transaction ID:</span>
                    <p className="font-medium">{fineResult.id}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">Fine Amount Paid:</span>
                    <p className="font-medium text-green-600">${fineResult.fineAmount?.toFixed(2)}</p>
                  </div>
                  <div>
                    <span className="text-gray-500">Fine Status:</span>
                    <StatusBadge status={fineResult.finePaid ? 'PAID' : 'UNPAID'} type="default" />
                  </div>
                </div>
              </div>
            )}
          </Card.Body>
        </Card>
      )}
    </div>
  );
}

export default Circulation;
