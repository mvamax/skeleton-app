import React from 'react';


// TODO: Rework React.element appearance
module.exports = (props, context) => {
    let state = context.states[props.name];
    let error = context.errors[props.name];

    if (React.isValidElement(error) || (error && error.indexOf(':')!==-1)) {
        return error;
    }

    return state && state.isUsed && state.isChanged && error;
};
