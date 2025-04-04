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
    public partial class FormLogin : Form
    {
        public FormLogin()
        {
            InitializeComponent();
        }

        private void FormLogin_Load(object sender, EventArgs e)
        {
            Support.enable(this);

            tbUsername.Text = "admin";
            tbPassword.Text = "admin123";
        }

        private void bunifuCheckBox1_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (bunifuCheckBox1.Checked)
            {
                tbPassword.UseSystemPasswordChar = true;
            } else
            {
                tbPassword.UseSystemPasswordChar = false;
            }
        }

        private void bunifuButton1_Click(object sender, EventArgs e)
        {
            if (tbUsername.Text == string.Empty || tbPassword.Text == string.Empty)
            {
                Support.msw("All fields must be filled");
                return;
            }

            var db = new DataBaseDataContext();

            var query = db.Admins.FirstOrDefault(x => x.username == tbUsername.Text && x.password == tbPassword.Text);

            if (query != null)
            {
                new FormMain().Show();
                Hide();
            } else
            {
                Support.msw("Your data is not valid!!, please try again");
            }
        }

        private void bunifuButton2_Click(object sender, EventArgs e)
        {
            tbUsername.Clear();
            tbPassword.Clear();
        }
    }
}
