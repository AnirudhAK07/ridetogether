import { useState } from 'react'
import './App.css'
import SavedTripsSection from './components/SavedTripsSection.jsx'
import CreateTripForm from './components/CreateTripForm.jsx'
import PollsSection from './components/PollsSection.jsx'
function rupeesToPaise(amountText) {
  const match = amountText.trim().match(/^(\d+)(?:\.(\d{1,2}))?$/)

  if (match === null) {
    return null
  }

  const rupees = Number(match[1])
  const paise = Number((match[2] ?? '').padEnd(2, '0'))

  return rupees * 100 + paise
}

function App() {
  const [tripName, setTripName] = useState('')
  const [message, setMessage] = useState('')
  const [createdTrip, setCreatedTrip] = useState(null)
  const [isCreating, setIsCreating] = useState(false)
  const [memberName, setMemberName] = useState('')
const [members, setMembers] = useState([])
const [isAddingMember, setIsAddingMember] = useState(false)
const [expenseDescription, setExpenseDescription] = useState('')
const [expensePaidBy, setExpensePaidBy] = useState('')
const [expenseAmount, setExpenseAmount] = useState('')
const [isAddingExpense, setIsAddingExpense] = useState(false)
const [expenses, setExpenses] = useState([])
const [settlements, setSettlements] = useState([])
const [isLoadingSettlements, setIsLoadingSettlements] = useState(false)
const [savedTrips, setSavedTrips] = useState([])
const [isLoadingTrips, setIsLoadingTrips] = useState(false)
const [tripDestination, setTripDestination] = useState('')
const [tripStartDate, setTripStartDate] = useState('')
const [tripEndDate, setTripEndDate] = useState('')
const [polls, setPolls] = useState([])

  async function handleSubmit(event) {
  event.preventDefault()

  const trimmedTripName = tripName.trim()
  const trimmedDestination = tripDestination.trim()

  if (trimmedTripName === '') {
    setMessage('Please enter a trip name.')
    return
  }

  if (trimmedDestination === '') {
    setMessage('Please enter a destination.')
    return
  }

  if (tripStartDate === '' || tripEndDate === '') {
    setMessage('Please select the trip dates.')
    return
  }

  if (tripEndDate < tripStartDate) {
    setMessage('The end date must be after the start date.')
    return
  }

  setIsCreating(true)
  setMessage('')

  try {
    const response = await fetch('/api/trips', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: trimmedTripName,
        destination: trimmedDestination,
        startDate: tripStartDate,
        endDate: tripEndDate,
      }),
    })

    if (!response.ok) {
      throw new Error('Could not create the trip.')
    }

    const trip = await response.json()

    setCreatedTrip(trip)
    setMembers([])
    setExpenses([])
    setSettlements([])
    setTripName('')
    setTripDestination('')
    setTripStartDate('')
    setTripEndDate('')
    setPolls([])

    setMessage(`Created "${trip.name}".`)
  } catch (error) {
    setMessage(error.message)
  } finally {
    setIsCreating(false)
  }
}

  async function handleLoadTrips() {
  setIsLoadingTrips(true)
  setMessage('')

  try {
    const response = await fetch('/api/trips')

    if (!response.ok) {
      throw new Error('Could not load saved trips.')
    }

    const tripList = await response.json()

    setSavedTrips(tripList)
    setMessage(`${tripList.length} saved trip(s) loaded.`)
  } catch (error) {
    setMessage(error.message)
  } finally {
    setIsLoadingTrips(false)
  }
}

async function handleSelectTrip(trip) {
  setMessage('')

  try {
    const membersResponse = await fetch(
      `/api/trips/${trip.id}/members`,
    )
    const expensesResponse = await fetch(
      `/api/trips/${trip.id}/expenses`,
    )
    const pollsResponse = await fetch(
      `/api/trips/${trip.id}/polls`,
    )

    if (
      !membersResponse.ok ||
      !expensesResponse.ok ||
      !pollsResponse.ok
    ) {
      throw new Error('Could not load the trip details.')
    }

    const memberList = await membersResponse.json()
    const expenseList = await expensesResponse.json()
    const pollList = await pollsResponse.json()

    setCreatedTrip(trip)
    setMembers(memberList)
    setExpenses(expenseList)
    setPolls(pollList)
    setSettlements([])
    setMessage(`Loaded "${trip.name}".`)
  } catch (error) {
    setMessage(error.message)
  }
}

async function handleCreatePoll(question, options) {
  if (createdTrip === null) {
    throw new Error('Create or load a trip before adding a poll.')
  }

  const response = await fetch(
    `/api/trips/${createdTrip.id}/polls`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        question,
        options,
      }),
    },
  )

  if (!response.ok) {
    throw new Error('Could not create the poll.')
  }

  const pollsResponse = await fetch(
    `/api/trips/${createdTrip.id}/polls`,
  )

  if (!pollsResponse.ok) {
    throw new Error('Could not refresh polls.')
  }

  const pollList = await pollsResponse.json()

  setPolls(pollList)
}

async function handleVote(voterName, pollId, optionId) {
  if (createdTrip === null) {
    throw new Error('Create or load a trip before voting.')
  }

  const response = await fetch(
    `/api/trips/${createdTrip.id}/polls/${pollId}/votes`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        voterName,
        optionId,
      }),
    },
  )

  if (!response.ok) {
    throw new Error('Could not record the vote.')
  }

  const pollsResponse = await fetch(
    `/api/trips/${createdTrip.id}/polls`,
  )

  if (!pollsResponse.ok) {
    throw new Error('Could not refresh polls.')
  }

  const pollList = await pollsResponse.json()

  setPolls(pollList)
}


  async function handleAddMember(event) {
  event.preventDefault()

  const trimmedMemberName = memberName.trim()

  if (trimmedMemberName === '') {
    setMessage('Please enter a member name.')
    return
  }

  if (createdTrip === null) {
    setMessage('Create a trip before adding members.')
    return
  }

  setIsAddingMember(true)
  setMessage('')

  try {
    const response = await fetch(
      `/api/trips/${createdTrip.id}/members`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: trimmedMemberName,
        }),
      },
    )

    if (!response.ok) {
      throw new Error('Could not add the member.')
    }

    setMembers((currentMembers) => [
      ...currentMembers,
      trimmedMemberName,
    ])
    setMemberName('')
    setMessage(`${trimmedMemberName} was added.`)
  } catch (error) {
    setMessage(error.message)
  } finally {
    setIsAddingMember(false)
  }
}


async function loadExpenses() {
  if (createdTrip === null) {
    return
  }

  const response = await fetch(
    `/api/trips/${createdTrip.id}/expenses`,
  )

  if (!response.ok) {
    throw new Error('Could not load expenses.')
  }

  const expenseList = await response.json()
  setExpenses(expenseList)
}


async function handleCalculateSettlements() {
  if (createdTrip === null) {
    setMessage('Create a trip before calculating settlements.')
    return
  }

  setIsLoadingSettlements(true)
  setMessage('')

  try {
    const response = await fetch(
      `/api/trips/${createdTrip.id}/settlements`,
    )

    if (!response.ok) {
      throw new Error('Could not calculate settlements.')
    }

    const settlementList = await response.json()

    setSettlements(settlementList)
    setMessage('Settlements calculated.')
  } catch (error) {
    setMessage(error.message)
  } finally {
    setIsLoadingSettlements(false)
  }
}
async function handleAddExpense(event) {
  event.preventDefault()

  const amountInPaise = rupeesToPaise(expenseAmount)

  if (createdTrip === null) {
    setMessage('Create a trip before adding expenses.')
    return
  }

  if (expenseDescription.trim() === '') {
    setMessage('Please enter an expense description.')
    return
  }

  if (expensePaidBy === '') {
    setMessage('Choose the member who paid.')
    return
  }

  if (amountInPaise === null || amountInPaise <= 0) {
    setMessage('Enter a valid positive amount with up to two decimal places.')
    return
  }

  setIsAddingExpense(true)
  setMessage('')

  try {
    const response = await fetch(
      `/api/trips/${createdTrip.id}/expenses`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          description: expenseDescription.trim(),
          paidBy: expensePaidBy,
          amountInPaise,
        }),
      },
    )

    if (!response.ok) {
      throw new Error('Could not add the expense.')
    }
    await loadExpenses()

    setExpenseDescription('')
    setExpensePaidBy('')
    setExpenseAmount('')
    setMessage('Expense added.')
  } catch (error) {
    setMessage(error.message)
  } finally {
    setIsAddingExpense(false)
  }
}

  return (
    <main className="app-shell">
      <header className="app-hero">
        <span className="eyebrow">GROUP RIDE PLANNER</span>
        <h1>RideTogether</h1>
        <p>Plan memorable rides, split every expense fairly, and decide together.</p>
      </header>
<CreateTripForm
  tripName={tripName}
  tripDestination={tripDestination}
  tripStartDate={tripStartDate}
  tripEndDate={tripEndDate}
  isCreating={isCreating}
  onTripNameChange={setTripName}
  onDestinationChange={setTripDestination}
  onStartDateChange={setTripStartDate}
  onEndDateChange={setTripEndDate}
  onSubmit={handleSubmit}
/>

      <SavedTripsSection
  savedTrips={savedTrips}
  isLoadingTrips={isLoadingTrips}
  onLoadTrips={handleLoadTrips}
  onSelectTrip={handleSelectTrip}
/>
      {message && <p className="app-message">{message}</p>}

      {createdTrip && (
        <div className="active-trip">
          <span>Active trip</span>
          <strong>{createdTrip.name}</strong>
        </div>
      )}

      {createdTrip && (
<PollsSection
  polls={polls}
  members={members}
  onCreatePoll={handleCreatePoll}
  onVote={handleVote}
/>
)}

      {createdTrip && (
  <section>
    <h2>Add member</h2>

    <form onSubmit={handleAddMember}>
      <label htmlFor="member-name">Member name</label>
      <input
        id="member-name"
        value={memberName}
        onChange={(event) => setMemberName(event.target.value)}
        placeholder="Example: Rahul"
      />

      <button type="submit" disabled={isAddingMember}>
        {isAddingMember ? 'Adding...' : 'Add member'}
      </button>
    </form>

    {members.length > 0 && (
      <ul>
        {members.map((member) => (
          <li key={member}>{member}</li>
        ))}
      </ul>
    )}
  </section>
)}

{createdTrip && members.length > 0 && (
  <section>
    <h2>Add expense</h2>

    <form onSubmit={handleAddExpense}>
      <label htmlFor="expense-description">Description</label>
      <input
        id="expense-description"
        value={expenseDescription}
        onChange={(event) => setExpenseDescription(event.target.value)}
        placeholder="Example: Fuel"
      />

      <label htmlFor="expense-paid-by">Paid by</label>
      <select
        id="expense-paid-by"
        value={expensePaidBy}
        onChange={(event) => setExpensePaidBy(event.target.value)}
      >
        <option value="">Choose a member</option>

        {members.map((member) => (
          <option key={member} value={member}>
            {member}
          </option>
        ))}
      </select>

      <label htmlFor="expense-amount">Amount in rupees</label>
      <input
        id="expense-amount"
        value={expenseAmount}
        onChange={(event) => setExpenseAmount(event.target.value)}
        placeholder="Example: 15000.00"
        inputMode="decimal"
      />

      <button type="submit" disabled={isAddingExpense}>
        {isAddingExpense ? 'Adding...' : 'Add expense'}
      </button>
    </form>
  </section>
)}
{expenses.length > 0 && (
  <section>
    <h2>Expenses</h2>

    <ul>
      {expenses.map((expense) => (
        <li
          key={`${expense.description}-${expense.paidBy}-${expense.amount}`}
        >
          {expense.description}: {expense.paidBy} paid {expense.amount}
        </li>
      ))}
    </ul>
  </section>
)}

{createdTrip && expenses.length > 0 && (
  <section>
    <h2>Settlements</h2>

    <button
      type="button"
      onClick={handleCalculateSettlements}
      disabled={isLoadingSettlements}
    >
      {isLoadingSettlements
        ? 'Calculating...'
        : 'Calculate settlements'}
    </button>

    {settlements.length > 0 && (
      <ul>
        {settlements.map((settlement) => (
          <li
            key={`${settlement.from}-${settlement.to}-${settlement.amount}`}
          >
            {settlement.from} pays {settlement.to} {settlement.amount}
          </li>
        ))}
      </ul>
    )}
  </section>
)}



    </main>
  )
}

export default App
