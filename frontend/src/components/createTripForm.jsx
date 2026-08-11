function CreateTripForm({
  tripName,
  tripDestination,
  tripStartDate,
  tripEndDate,
  isCreating,
  onTripNameChange,
  onDestinationChange,
  onStartDateChange,
  onEndDateChange,
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

      <label htmlFor="trip-destination">Destination</label>
      <input
        id="trip-destination"
        value={tripDestination}
        onChange={(event) => onDestinationChange(event.target.value)}
        placeholder="Example: Coorg"
      />

      <label htmlFor="trip-start-date">Start date</label>
      <input
        id="trip-start-date"
        type="date"
        value={tripStartDate}
        onChange={(event) => onStartDateChange(event.target.value)}
      />

      <label htmlFor="trip-end-date">End date</label>
      <input
        id="trip-end-date"
        type="date"
        value={tripEndDate}
        onChange={(event) => onEndDateChange(event.target.value)}
      />

      <button type="submit" disabled={isCreating}>
        {isCreating ? 'Creating...' : 'Create trip'}
      </button>
    </form>
  )
}

export default CreateTripForm