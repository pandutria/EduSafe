using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace EduSafe
{
    public partial class FormOnBoarding : Form
    {
        public FormOnBoarding()
        {
            InitializeComponent();
        }

        private void FormOnBoarding_Load(object sender, EventArgs e)
        {
            Support.enable(this);
        }

        private void bunifuButton2_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void bunifuButton1_Click(object sender, EventArgs e)
        {
            new FormLogin().Show();
            Hide(); 
        }
    }
}
