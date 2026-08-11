function SavedTripsSection({
  savedTrips,
  isLoadingTrips,
  onLoadTrips,
  onSelectTrip,
}) {
  return (
    <section>
      <h2>Saved trips</h2>

      <button
        type="button"
        onClick={onLoadTrips}
        disabled={isLoadingTrips}
      >
        {isLoadingTrips ? 'Loading...' : 'Load saved trips'}
      </button>

      {savedTrips.length > 0 && (
        <ul>
          {savedTrips.map((trip) => (
            <li key={trip.id}>
              <button
                type="button"
                onClick={() => onSelectTrip(trip)}
              >
                <strong>{trip.name}</strong>
<br />
{trip.destination || 'Destination not set'}
<br />
{trip.startDate || 'No start date'} to {trip.endDate || 'No end date'}
              </button>
            </li>
          ))}
        </ul>
      )}
    </section>
  )
}

export default SavedTripsSection