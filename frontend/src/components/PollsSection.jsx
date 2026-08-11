import { useState } from 'react'

function PollsSection({
  polls,
  members,
  onCreatePoll,
  onVote,
}) {
  const [question, setQuestion] = useState('')
  const [optionsText, setOptionsText] = useState('')
  const [voterName, setVoterName] = useState('')
  const [message, setMessage] = useState('')
  const [isCreating, setIsCreating] = useState(false)
  const [isVoting, setIsVoting] = useState(false)

  async function handleSubmit(event) {
    event.preventDefault()

    const optionList = optionsText
      .split(',')
      .map((option) => option.trim())
      .filter((option) => option !== '')

    if (question.trim() === '') {
      setMessage('Please enter a question.')
      return
    }

    if (optionList.length < 2) {
      setMessage('Enter at least two comma-separated options.')
      return
    }

    setIsCreating(true)
    setMessage('')

    try {
      await onCreatePoll(question.trim(), optionList)
      setQuestion('')
      setOptionsText('')
      setMessage('Poll created.')
    } catch (error) {
      setMessage(error.message)
    } finally {
      setIsCreating(false)
    }
  }

  async function handleVote(pollId, optionId) {
    if (voterName === '') {
      setMessage('Choose a voter before voting.')
      return
    }

    setIsVoting(true)
    setMessage('')

    try {
      await onVote(voterName, pollId, optionId)
      setMessage(`${voterName}'s vote was recorded.`)
    } catch (error) {
      setMessage(error.message)
    } finally {
      setIsVoting(false)
    }
  }

  return (
    <section className="polls-section">
      <h2>Group decisions</h2>

      <form onSubmit={handleSubmit}>
        <label htmlFor="poll-question">Question</label>
        <input
          id="poll-question"
          value={question}
          onChange={(event) => setQuestion(event.target.value)}
          placeholder="Example: Which route should we take?"
        />

        <label htmlFor="poll-options">Options</label>
        <input
          id="poll-options"
          value={optionsText}
          onChange={(event) => setOptionsText(event.target.value)}
          placeholder="Example: Mysuru route, Chikmagalur route"
        />

        <button type="submit" disabled={isCreating}>
          {isCreating ? 'Creating...' : 'Create poll'}
        </button>
      </form>

      <label htmlFor="poll-voter">Voting as</label>
      <select
        id="poll-voter"
        value={voterName}
        onChange={(event) => setVoterName(event.target.value)}
      >
        <option value="">Choose a member</option>
        {members.map((member) => (
          <option key={member} value={member}>
            {member}
          </option>
        ))}
      </select>

      {message && <p>{message}</p>}

      {polls.length === 0 ? (
        <p>No polls yet.</p>
      ) : (
        polls.map((poll) => (
          <div key={poll.id}>
            <h3>{poll.question}</h3>

            <ul>
              {poll.options.map((option) => (
                <li key={option.id}>
                  {option.text} - {option.voteCount} vote(s)

                  <button
                    type="button"
                    onClick={() => handleVote(poll.id, option.id)}
                    disabled={isVoting}
                  >
                    Vote for this option
                  </button>
                </li>
              ))}
            </ul>
          </div>
        ))
      )}
    </section>
  )
}

export default PollsSection
