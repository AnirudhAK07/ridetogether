function CreateTripForm({
  tripName,
  isCreating,
  onTripNameChange,
  onSubmit,
}) {
  return (
    <form onSubmit={onSubmit}>
      <label htmlFor="trip-name">Trip name</label>
      <input
        id="trip-name"
        value={tripName}
        onChange={(event) => onTripNameChange(event.target.value)}
        placeholder="Example: Coorg Weekend Ride"
      />

      <button type="submit" disabled={isCreating}>
        {isCreating ? 'Creating...' : 'Create trip'}
      </button>
    </form>
  )
}

export default CreateTripForm