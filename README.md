Risk Game (Terminal Version)
Overview
This is a terminal-based implementation of the classic board game Risk. The game features basic functionalities and a working AI to simulate gameplay.

Features
Basic game setup and mechanics
Command-line interface
Single-player mode with AI opponents
Simple AI logic
Installation
Clone the repository:
sh
Kopiera kod
git clone https://github.com/yourusername/risk-terminal.git
Navigate to the project directory:
sh
Kopiera kod
cd risk-terminal
Install dependencies (if any):
sh
Kopiera kod
pip install -r requirements.txt
Usage
Start the game:
sh
Kopiera kod
python main.py
Follow the on-screen instructions to play.
Commands
deploy <territory> <num_troops>: Deploy troops to a territory.
attack <from_territory> <to_territory>: Attack an adjacent territory.
fortify <from_territory> <to_territory> <num_troops>: Move troops between your territories.
AI
The AI has basic decision-making capabilities:

Deploys troops randomly.
Attacks adjacent territories with fewer troops.
Fortifies territories randomly.
Contributing
Fork the repository.
Create a new branch (git checkout -b feature-branch).
Commit your changes (git commit -m 'Add new feature').
Push to the branch (git push origin feature-branch).
Open a Pull Request.
License
This project is licensed under the KTH License. See the LICENSE file for details.

Enjoy playing Risk in your terminal!
