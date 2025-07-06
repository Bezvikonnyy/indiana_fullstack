import React from 'react';
import hatIcon from '../assets/hat.png';

function Header() {
    return (
        <header style={styles.header}>
            <div style={styles.titleContainer}>
                <span style={styles.titleText}>Indian</span>
                <span style={styles.lastLetterWrapper}>
          <span style={styles.lastLetter}>a</span>
          <img src={hatIcon} alt="Hat" style={styles.hatIcon} />
        </span>
            </div>
        </header>
    );
}

const styles = {
    header: {
        backgroundColor: '#2e7d32',
        height: '4rem',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'relative',
        boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
    },
    titleContainer: {
        display: 'flex',
        alignItems: 'baseline',
        fontSize: '1.8rem',
        fontFamily: 'Georgia, serif',
        color: 'white',
    },
    titleText: {
        fontWeight: 'bold',
    },
    lastLetterWrapper: {
        position: 'relative',
        display: 'inline-block',
        width: '1.8rem',
        height: '2rem',
    },
    lastLetter: {
        fontWeight: 'bold',
    },
    hatIcon: {
        position: 'absolute',
        top: '0.4rem',
        left: '-0.28rem',
        width: '1.6rem',
        transform: 'rotate(-15deg)',
        pointerEvents: 'none',
    },
};

export default Header;
