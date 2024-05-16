import React, { useState } from 'react';

const PaymentForm = ({ onConfirm, onCancel, sum }) => {
    const [cardNumber, setCardNumber] = useState('');
    const [cardCode, setCardCode] = useState('');

    const handleConfirm = () => {
        onConfirm(cardNumber, cardCode);
    };

    const handleCancel = () => {
        onCancel();
    };

    return (
        <div className="booking1-form">
            <h3>Booking Information</h3><br/>
            <h4>Total: {sum}$</h4><br/>
            <label className="form-label">
                {'Card Number:'}
                <input
                    type="text"
                    value={cardNumber}
                    onChange={(e) => setCardNumber(e.target.value)}
                    maxLength={16}
                />
            </label><br/>
            <label className="form-label">
                {'Verify Code:'}
                <input
                    type="text"
                    value={cardCode}
                    onChange={(e) => setCardCode(e.target.value)}
                    maxLength={3}
                />
            </label><br/>
            <div>
                <button className="login-button" onClick={handleConfirm}>
                    Confirm
                </button>{'   '}
                <button className="login-button" onClick={handleCancel}>
                    Cancel
                </button>
            </div>
        </div>
    );
};

export default PaymentForm;